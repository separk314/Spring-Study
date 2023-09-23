package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // JpaRepository<T, ID>
    // T: Entity, ID: Entity의 PK의 데이터 타입

    List<Member> findByName(String name);
    /*
        - findByName
        select m from Member m where m.name = ?
        이렇게 자동으로 쿼리를 만들어준다.
     */
}
