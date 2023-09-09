package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        /*
            V1: 엔티티를 직접 노출
         */
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            // LAZY 강제 초기화(쿼리 실행시키기) -> 원하는 값만 가져옴
            order.getMember().getName();
            order.getDelivery().getAddress();
            // orderItems는 초기화되지 않는다
        }

        return all;
    }

    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        /*
            V2: 엔티티를 DTO로 변환하는 방법
            - 지연 로딩으로 인한 1+N+N 문제 발샏
         */
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        List<SimpleOrderDto> collect = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        /*
            V3: 패치 조인 최적화
         */
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        /*
            V4: JPA에서 DTO 바로 조회(성능 최적화)
         */
        return orderRepository.findOrderDtos();
    }


    /*
        DTO
     */
    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;    // 배송지 주소

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();     // LAZY 초기화(DB 쿼리 실행)
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();   // LAZY 초기화(DB 쿼리 실행)
        }
    }
}
