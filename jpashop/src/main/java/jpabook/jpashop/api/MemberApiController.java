package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController     // @Controller + @ResponseBody
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 생성
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        /*
            @RequestBody: Json으로 온 Body를 Member으로 바꿔줌
            @Valid: 회원의 Validation 진행(Member의 name에 @NotEmpty를 검사한다)
         */
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 이름 수정
     * @return
     */
    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        // 수정: 변경 감지 사용
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    /**
     * 회원 리스트 조회
     */
    @GetMapping("api/v1/members")
    public List<Member> membersV1() {
        List<Member> members = memberService.findMembers();
        System.out.println(members);
        return memberService.findMembers();
    }




    /*
        DTO
     */
    @Data
    static class CreateMemberRequest {  // Request DTO
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;    // 등록된 member id값을 반환

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


}
