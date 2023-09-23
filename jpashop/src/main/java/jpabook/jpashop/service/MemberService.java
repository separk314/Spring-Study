package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)     // Default 값으로 readOnly=ture -> 조회할 때 성능 최적화
@RequiredArgsConstructor
public class MemberService {

    @Autowired      // @Autowired: 자동으로 injection
    private final MemberRepository memberRepository;


    /**
     * 회원 가입
     */
    @Transactional      // data write -> readOnly=false
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복된 회원인지 검사
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    /**
     * 회원 단건 조회
     * @param memberId
     */
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);   // 영속 상태 -> JPA가 update query 실행시킴
    }
}
