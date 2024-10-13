package jpa.mvc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue //auto : h2이면 그냥 SEQUENCE 전략 ! sequence 객체로부터 하나씩 만듬 ㅇㅇ
    @Column(name = "ORDER_ID")
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    /**
     * 연관관계 편의 메서드 , 한 메서드로 연관관계 설정해버리면 좋음 , 애초에 setter 안 쓰니 이렇게 도메인 내부에서 연관관계 메서드를 쓰는 것 이거 자체가 도메인 주도 설계네
     * 연관관계 객체 필드를 갖고 있으니 그것에 대한 변경을 위해 도메인 내부에서 개발해준 것
     */
    public void addMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    /**
     * 생성 메서드(생성 편의 메서드 , 정적 팩토리 메서드)
     */
    //주문 : 주문상품 , 배송 , 회원과 연관관계 어떤 회원이 어떤 상품을 주문해서 배송 상태가 어떤지?
    public static Order createOrder(Delivery delivery, Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        order.addDelivery(delivery);
        order.addMember(member); //
        for (OrderItem orderItem : orderItems) {
            orderItem.addOrder(order);
        }
        return order;
    }
    /**
     * 비즈니스 로직 , 주문 취소 , 취소 시 수량 증가
     */
    //주문상태 cancel + 주문 수량 원복
    public void cancelOrder(){
        //검증 로직
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능");
        }
        this.setOrderStatus(OrderStatus.CANCEL);
        //OrderItem은 다 이므로 즉 주문상품 각각을 전부 캔슬시켜줘야됨
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }

    }

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }


}
