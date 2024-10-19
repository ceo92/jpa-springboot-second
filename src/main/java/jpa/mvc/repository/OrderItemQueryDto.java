package jpa.mvc.repository;

import jpa.mvc.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemQueryDto {

  private String itemName;
  private int orderPrice;
  private int count;

  public OrderItemQueryDto(OrderItem orderItem) {
    this.itemName = orderItem.getItem().getName();
    this.orderPrice = orderItem.getOrderPrice();
    this.count = orderItem.getCount();
  }

}
