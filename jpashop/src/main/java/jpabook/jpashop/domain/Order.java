package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")   // 실제 DB table 이름 지정해줌
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Order 입장에서: member가 여러 개의 주문 -> 다:1
    @JoinColumn(name = "member_id")   // mapping colunm(즉, FK), 연관관계 주인
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)    // ORDINARY 절대 사용 금지
    private OrderStatus status;     // 주문 상태 [ORDER, ENUM]


    //== 연관 관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);   // 연관관계에도 넣어준다
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        delivery.setOrder(this);    // 양방향 세팅
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}