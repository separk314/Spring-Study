package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;   // Springboot가 EntityManager 자동 생성

    // 저장하기
    public Long save(Member member) {
        em.persist(member);
        return member.getId();  // command랑 query를 분리(ID 정도만 반환)
    }

    // 조회하기
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
