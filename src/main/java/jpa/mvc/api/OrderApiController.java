package jpa.mvc.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jpa.mvc.Address;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderItem;
import jpa.mvc.domain.OrderStatus;
import jpa.mvc.repository.OrderRepository;
import jpa.mvc.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1(){
    List<Order> orderList = orderRepository.findAll(new OrderSearch());
    for (Order order : orderList) {
      order.getDelivery().getAddress(); //Order(1) => Delivery(2) => member(3) =>
      order.getMember().getName();
      order.getOrderItems().stream().map(orderItem -> orderItem.getItem().getName()).toList();
    }
    return orderList;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDto> ordersV2(){
    List<Order> orderList = orderRepository.findAll(new OrderSearch());
    List<OrderDto> list = orderList.stream().map(OrderDto::new).toList();
    return list;
  }
  @GetMapping("/api/v3/orders")
  public List<OrderDto> ordersV3(){
    List<Order> orderList = orderRepository.findAllWithOrderItems();
    List<OrderDto> list = orderList.stream().map(OrderDto::new).toList();
    return list;
  }





  @Data
  static class OrderDto{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      this.orderId = order.getId();
      this.name = order.getMember().getName(); //사람이름
      this.orderDate = order.getOrderDate();
      this.orderStatus = order.getOrderStatus();
      this.address = order.getDelivery().getAddress();
      this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).toList();
    }
  }

  @Data
  static class OrderItemDto{
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
      this.itemName = orderItem.getItem().getName();
      this.orderPrice = orderItem.getOrderPrice();
      this.count = orderItem.getCount();
    }
  }


}
