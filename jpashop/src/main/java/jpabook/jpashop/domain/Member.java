package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")   // Column명을 지정하지 않으면 이름이 id 그대로
    private Long id;

    private String name;

    @Embedded   // 내장 타입을 쓴다
    private Address address;

    // Member의 입장에서 order: Member 1명이 여러 상품을 주문 -> 일대다 관계
    @OneToMany(mappedBy = "member")  // order table의 member field에 의해 mapped
    private List<Order> orders = new ArrayList<>();


}
