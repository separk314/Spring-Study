package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * _ToOne 관계 최적화
 * Order -> Member (Many-To-One)
 * Order -> Delivery (One-To-One)
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order: all) {
            // LAZY 강제 초기화(쿼리 실행시키기) -> 원하는 값만 가져옴
            order.getMember().getName();
            order.getDelivery().getAddress();
            // orderItems는 초기화되지 않는다
        }

        return all;
    }
}
