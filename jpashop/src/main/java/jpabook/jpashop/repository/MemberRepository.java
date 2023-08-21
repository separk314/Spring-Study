package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository     // @Repository: Component scan으로 Spring bean 자동 등록
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext: Spring이 EntityManager 생성 후 주입
    // -> @RequiredArgsConstructor가 생성자 자동 생성
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);     // member 저장
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        // SQL과 비슷하지만 Entity(객체)에 대한 질의이다.
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
