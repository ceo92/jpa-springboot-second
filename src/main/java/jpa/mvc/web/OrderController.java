package jpa.mvc.web;

import java.util.ArrayList;
import jpa.mvc.domain.Book;
import jpa.mvc.domain.Item;
import jpa.mvc.domain.Member;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderItem;
import jpa.mvc.repository.OrderSearch;
import jpa.mvc.service.ItemService;
import jpa.mvc.service.MemberService;
import jpa.mvc.service.OrderService;
import jpa.mvc.web.dto.ItemDto;
import jpa.mvc.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    //주문 시 Delivery와 OrderItem은 연쇄적으로 같이 cascade 됨

    @GetMapping("order-form")
    public String getOrderForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("order-form")
    public String postOrderForm(@RequestParam("memberId") Long memberId
        , @RequestParam("itemId") Long itemId
        , @RequestParam("count") int count){
        orderService.order(memberId, itemId, count); //주문 로직이 회원 , 상품 , 개수를 넘기니 파라메터로 타임리프에서도 그렇게 받은 것
        return "redirect:/order-list";
    }

    @GetMapping("order-list")
    public String getOrderList(@ModelAttribute("orderSearch") OrderSearch orderSearch , Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";

    }

}
