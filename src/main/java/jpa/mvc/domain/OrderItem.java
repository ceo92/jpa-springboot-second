package jpa.mvc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;

@Entity
@Table(name = "ORDER_ITEM")
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue //auto : h2이면 그냥 SEQUENCE 전략 ! sequence 객체로부터 하나씩 만듬 ㅇㅇ
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;

    private int count;



    /**
     * 연관관계 설정 메서드
     */
    public void addOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item){
        this.item = item;
    }

    //== 주문 취소==//
    public void cancel() {
        getItem().addStock(count);
    }

    //1. 다수의 연관관계 설정 동시에 및 2. 비즈니스 로직 호출 : 생성 메서드
    //주문 상품은 상품만 생성해주면 됨
    //여기서 재고 까고 그 다음에 Order에 넘겨줌
    public static OrderItem createOrderItem(Item item, int orderPrice , int count){ //할인쿠폰 , 고객등급에 따라 주문 가격 달라질 수 있으니 파라메터로 받는게 맞음
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        orderItem.addItem(item); //Item과의 단방향 연관관계 설정
        item.removeStock(count); //주문했으니 Item에서 재고 줄어들게 해야됨
        return orderItem;
    }


    //== 조회 비즈니스 로직==//
    /**
     * 총 주문 가격 계산
     */
    public int getTotalPrice(){
        int totalPrice = getCount() * getOrderPrice();
        return totalPrice;
    }








}
