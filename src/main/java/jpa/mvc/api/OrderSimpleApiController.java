package jpa.mvc.api;

import java.util.List;
import jpa.mvc.Address;
import jpa.mvc.domain.Order;
import jpa.mvc.repository.OrderSearch;
import jpa.mvc.service.OrderService;
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
  public List<Order> findOrders(){
    List<Order> orders = orderService.findOrders(new OrderSearch());
    for (Order order : orders) {
      order.getMember().getName();
      order.getDelivery().getAddress();
    }
    return orders;
  }

}
