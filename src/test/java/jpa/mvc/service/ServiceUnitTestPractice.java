package jpa.mvc.service;

import jpa.mvc.Address;
import jpa.mvc.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceUnitTestPractice {
    @Test
    void aa(){
        //given
        Member member = getMember("memberA", new Address("cityA", "bb", "cc"));
        Item item = getItem("itemA", 30000, 100);

        Order order = Order.createOrder(new Delivery(), member, OrderItem.createOrderItem(item, item.getPrice(), 20));
        //when
        //order.cancelOrder();
        //then
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(80);
    }

    private Item getItem(String name, int price, int stockQuantity) {
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        return item;
    }

    private Member getMember(String username, Address address) {
        Member member = new Member();
        member.setName(username);
        member.setAddress(address);
        return member;
    }
}
