package jpa.mvc.repository;

import jpa.mvc.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String username;
    private OrderStatus orderStatus;
}
