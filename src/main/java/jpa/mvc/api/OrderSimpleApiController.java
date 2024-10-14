package jpa.mvc.api;

import java.time.LocalDateTime;
import java.util.List;
import jpa.mvc.Address;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderStatus;
import jpa.mvc.repository.OrderSearch;
import jpa.mvc.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderService orderService;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> findOrdersV1(){
    List<Order> orders = orderService.findOrders(new OrderSearch());
    for (Order order : orders) {
      order.getMember().getName();
      order.getDelivery().getAddress();
    }
    return orders;
  }

  //원래는 컬렉션 응답하면 확장에 어려우니 넘기면 안 되지만 예제이므로 ㅎㅎ
  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDto> findOrdersV2(){
    List<SimpleOrderDto> newList = orderService.findOrders(new OrderSearch()).stream().map(SimpleOrderDto::new).toList();
    return newList;
  }


  @GetMapping("/api/v3/simple-orders")
  public Result findOrdersV3(){
    List<SimpleOrderDto> newList = orderService.findOrders(new OrderSearch()).stream().map(SimpleOrderDto::new).toList();
    return new Result<>(newList); //이렇게 제네릭을 통해 일반적인 중괄호로 묶여진 JSON형태가 되게 해야됨 그래야 확장 가능 ㅇㅇ 추가적인 속성이 생겨도 !
  }


  @Data
  @AllArgsConstructor
  static class Result<T>{
    private T data;
  }

  @Data
  static class SimpleOrderDto{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order o){
      orderId = o.getId();
      name = o.getMember().getName();
      orderDate = o.getOrderDate();
      orderStatus = o.getOrderStatus();
      address = o.getMember().getAddress();
    }

  }

}
