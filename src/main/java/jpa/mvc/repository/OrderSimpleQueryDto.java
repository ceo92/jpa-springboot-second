package jpa.mvc.repository;

import java.time.LocalDateTime;
import jpa.mvc.Address;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSimpleQueryDto {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address; //VO여서 값처럼 동작함 ㅇㅇ o.address 써도 됨

  public OrderSimpleQueryDto(Order o){ //order1 , order1 => member , order1 => delivery , order2  , order2 => delivery 나감 ,
    //order3 , order3 => member , order3 => delivery , order4 , order4=>delivery
    orderId = o.getId();
    name = o.getMember().getName(); //지연로딩이니 일단 프록시 가짜 객체(reference)가 할당됐다가 호출 시점에 영속성 컨텍스트에서 쿼리가 나가며 초기화
    orderDate = o.getOrderDate();
    orderStatus = o.getOrderStatus();
    address = o.getDelivery().getAddress();
  }



  public OrderSimpleQueryDto(Long id , String name , LocalDateTime orderDate , OrderStatus orderStatus , Address address){ //order1 , order1 => member , order1 => delivery , order2  , order2 => delivery 나감 ,
    //order3 , order3 => member , order3 => delivery , order4 , order4=>delivery
    this.orderId = id;
    this.name = name; //지연로딩이니 일단 프록시 가짜 객체(reference)가 할당됐다가 호출 시점에 영속성 컨텍스트에서 쿼리가 나가며 초기화
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
  }





}
