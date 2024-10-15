package jpa.mvc;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpa.mvc.domain.Book;
import jpa.mvc.domain.Delivery;
import jpa.mvc.domain.Item;
import jpa.mvc.domain.Member;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class InitDb {

  private final InitDbService initDbService;

  @PostConstruct
  public void init(){
    initDbService.dbInit1();
    initDbService.dbInit2();

  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitDbService{
    private final EntityManager em;


    //한 회원이 두 상품
    public void dbInit1(){
      //한 회원이 JPA BOOK1,2를 각각 주문했음 ㅇㅇ ,즉 한 주문에 한 상품만 담김
      Member member = new Member();
      member.setName("memberA");
      member.setAddress(new Address("cityA" , "distanceA" , "zipCodeA"));
      em.persist(member);

      Book book1 = new Book();
      book1.setName("JPA BOOK 1");
      book1.setPrice(33000);
      book1.setStockQuantity(100);
      em.persist(book1);

      Book book2 = new Book();
      book2.setName("JPA BOOK 2");
      book2.setPrice(40000);
      book2.setStockQuantity(120);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 120000, 4);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 150000, 5);

      Delivery delivery1 = new Delivery();
      delivery1.setAddress(member.getAddress());
      Delivery delivery2 = new Delivery();
      delivery2.setAddress(member.getAddress());

      Order order1 = Order.createOrder(delivery1, member, orderItem1);
      em.persist(order1);//OrderItem , Delivery 전부 cascade 걸려있으니 연계적으로 Crud 될 것임

      Order order2 = Order.createOrder(delivery2, member, orderItem2);
      em.persist(order2);

    }


    public void dbInit2(){
      //한 회원이 JPA BOOK1,2를 주문했음 ㅇㅇ
      Member member = new Member();
      member.setName("memberB");
      member.setAddress(new Address("cityB" , "distanceB" , "zipCodeB"));
      em.persist(member);

      Book book1 = new Book();
      book1.setName("SPRING BOOK 1");
      book1.setPrice(20000);
      book1.setStockQuantity(50);
      em.persist(book1);

      Book book2 = new Book();
      book2.setName("SPRING BOOK 2");
      book2.setPrice(35000);
      book2.setStockQuantity(90);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 80000, 4);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 70000, 2);

      Delivery delivery1 = new Delivery();
      delivery1.setAddress(member.getAddress());
      Delivery delivery2 = new Delivery();
      delivery2.setAddress(member.getAddress());

      Order order1 = Order.createOrder(delivery1, member, orderItem1);
      em.persist(order1);//OrderItem , Delivery 전부 cascade 걸려있으니 연계적으로 Crud 될 것임

      Order order2 = Order.createOrder(delivery2, member, orderItem2);
      em.persist(order2);

    }





  }

}
