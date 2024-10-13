package jpa.mvc.service;

import java.util.List;
import jpa.mvc.domain.*;
import jpa.mvc.repository.ItemRepository;
import jpa.mvc.repository.MemberRepository;
import jpa.mvc.repository.OrderRepository;
import jpa.mvc.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) //서비스 대부분 로직이 단순 조회만 하면 성능 효율을 위해 readOnly = true 해주고 데이터 crud가 일어나는 부분에 추가로 @Transactional 걸어주는게 좋음
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;



    //각 계층 간에는 id로 소통?
    //주문할 때는 누군지 어떤상품(Item)인지 배송지 어딘지(Delivery) 입력해야됨
    //실제 비즈니스 로직은 ㅓ
    @Transactional //주문 => 연관관계(회원 , 주문상품 , 배송)
    public Long order(Long memberId , Long itemId , int count){
        Member member = memberRepository.findById(memberId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); //회원의 주소 정보를 그대로 배송지로 지정 , 실제론 이러면 안되겠지만 ㅋㅋ
        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //주문 상품은 여러 개 올 수 있는데 ..
        Order order = Order.createOrder(delivery, member, orderItem);// 주문 상품을 만들고 => 주문해야됨 당연히 ㅇㅇ , 응집력 있는 설계가 가능하니 객체지향적인 , 객체 생성 코드 여기만 건들면 됨 , 또한 setter도 닫을 수 있으므로 이거 호출한 순간 객체 생성이 끝남 , 더 이상의 데이터 변경이 필요가 업승ㅁ!
        orderRepository.save(order); //원래는 Delivery랑 OrderItem 전부 persist로 저장하고 id 반환해야되는데 cascade를 통해 연쇄적으로 persist됨
        return order.getId();
    }


    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.cancelOrder();
    }


    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }






}
