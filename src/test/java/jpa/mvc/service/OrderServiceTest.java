package jpa.mvc.service;

import jakarta.persistence.EntityManager;
import jpa.mvc.Address;
import jpa.mvc.domain.*;
import jpa.mvc.exception.NotEnoughStockException;
import jpa.mvc.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired private EntityManager em;
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderService orderService;

    @Test
    void order() {
        //given
        Member member = getMember("memberA", new Address("덕성대로", "12길", "97830"));

        Item item = getItem("종만북", 45000, 20);

        //1. 재고 줄어들었는지 , 2.주문 상태 ORDER이 됐는지
        //when
        Long orderedId = orderService.order(member.getId(), item.getId(), 10);

        //then
        Order findOrder = orderRepository.findById(orderedId).orElseThrow();
        Assertions.assertThat(findOrder.getId()).isEqualTo(orderedId);
        Assertions.assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER); //주문 상태 Order 체크
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(10);// 재고 줄어들었는지?
        Assertions.assertThat(findOrder.getOrderItems().size()).isEqualTo(1); //주문 수량이 한 개인지?
        Assertions.assertThat(findOrder.getTotalPrice()).isEqualTo(45000 * 10); //주문 가격이 올바른지?
    }



    @Test
    @DisplayName("재고 없을 시 예외 발생 테스트")
    void orderException(){
        //given
        Member member = getMember("memberA", new Address("덕성대로", "12길", "97830"));

        Item item = getItem("종만북", 45000, 20);

        //1. 재고 줄어들었는지 , 2.주문 상태 ORDER이 됐는지
        //when & then
        Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), 21))
                .isInstanceOf(NotEnoughStockException.class)
                .hasMessage("재고가 부족합니다");
    }
    //테스트 원칙 : 반복성 독립성을 지키기 위해 예외 테스트와 정상 테스트 따로 보자 !
    @Test
    void cancelOrder() {
        //given
        Member member = getMember("memberA", new Address("덕성대로", "12길", "97830"));

        Item item = getItem("종만북", 45000, 20);

        //1. 재고 줄어들었는지 , 2.주문 상태 ORDER이 됐는지
        //when
        Long orderedId = orderService.order(member.getId(), item.getId(), 12);
        orderService.cancelOrder(orderedId);

        Order findOrder = orderRepository.findById(orderedId).orElseThrow();
        //Assertions.assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(20);
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(20);
        Assertions.assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    private Item getItem(String name, int price, int stockQuantity) {
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

    private Member getMember(String username, Address address) {
        Member member = new Member();
        member.setName(username);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}