package jpa.mvc.api;

import java.time.LocalDateTime;
import java.util.List;
import jpa.mvc.Address;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderStatus;
import jpa.mvc.repository.OrderRepository;
import jpa.mvc.repository.OrderSearch;
import jpa.mvc.repository.OrderSimpleQueryDto;
import jpa.mvc.repository.order.simplequery.OrderSimpleQueryRepository;
import jpa.mvc.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Simple;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;
  private final OrderSimpleQueryRepository orderSimpleQueryRepository;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> findOrdersV1(){
    List<Order> orders = orderRepository.findAll(new OrderSearch());
    for (Order order : orders) {
      order.getMember().getName();
      order.getDelivery().getAddress();
    }
    return orders;
  }

  //원래는 컬렉션 응답하면 확장에 어려우니 넘기면 안 되지만 예제이므로 ㅎㅎ
  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDto> findOrdersV2(){
    List<SimpleOrderDto> newList = orderRepository.findAll(new OrderSearch())
        .stream().map(SimpleOrderDto::new).toList();
    return newList;
  }

  @GetMapping("/api/v3/simple-orders")
  public List<SimpleOrderDto> findOrdersV3(){
    return orderRepository.findAllWithMemberDelivery()
        .stream().map(SimpleOrderDto::new).toList(); //미리 한 쿼리로 가져와서 뿌리는 것 즉 1 + N이 아닌 1번의 쿼리로 나감
  }

  @GetMapping("/api/v4/simple-orders")
  public List<OrderSimpleQueryDto> ordersV4(){
    return orderSimpleQueryRepository.findOrderDtos();
  }



  @Data
  static class SimpleOrderDto{
    //API 스펙에 맞춰서 딱딱 이름들이 잘 반환됨
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order o){ //order1 , order1 => member , order1 => delivery , order2  , order2 => delivery 나감 ,
      //order3 , order3 => member , order3 => delivery , order4 , order4=>delivery
      orderId = o.getId();
      name = o.getMember().getName(); //지연로딩이니 일단 프록시 가짜 객체(reference)가 할당됐다가 호출 시점에 영속성 컨텍스트에서 쿼리가 나가며 초기화
      orderDate = o.getOrderDate();
      orderStatus = o.getOrderStatus();
      address = o.getDelivery().getAddress();
    }
  }

}
