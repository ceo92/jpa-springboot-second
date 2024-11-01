package jpa.mvc.repository;

import java.time.LocalDateTime;
import java.util.List;
import jpa.mvc.Address;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderQueryDto {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

  public OrderQueryDto(Order order) {
    this.orderId = order.getId();
    this.name = order.getMember().getName(); //사람이름
    this.orderDate = order.getOrderDate();
    this.orderStatus = order.getOrderStatus();
    this.address = order.getDelivery().getAddress();
  }
}
