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


/**
 * 총 주문 2개
 * userA
 *  * JPA1 BOOK
 *  * JPA2 BOOK
 * userB
 *  * SPRING1 BOOK
 *  * SPRING2 BOOK
 */
//그치 이게 맞지;; 씨발 , 한 회원이 왜 주문을 따로 두 번해 두 상품에 대해서 한 주문을 하는 게 맞지 ㅅㅂ

@Component
@Transactional
@RequiredArgsConstructor
public class InitDb {

  private final InitDbService initDbService;

  @PostConstruct
  public void init(){
    initDbService.dbInit1();
    initDbService.dbInit2();
    initDbService.dbInit3();

  }

  @Component
  @Transactional //이런 식으로 별도의 빈으로 등록해야됨 , 스프링 라이프 사이클로 인해 ;; 그래서 따로 내부 클래스를 파주고 해당 컴포넌트 안에서 초기화 처리 해준 것
  @RequiredArgsConstructor
  static class InitDbService{
    private final EntityManager em;
    //a 회원은 a,b 상품을 각각 주문하였음
    //b 회원은 c,d 상품을 각각 주문하였음
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


      //주문 목록 즉 한 회원이 장바구니에 두 상품을 담은 것 , 그거 바탕으로 한 주문으로 시켜야겠지 ㅇㅇ 한 배송에
      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);


      Delivery delivery = new Delivery(); //한 회원이 두 아이템을 각각 배송하려고 함
      delivery.setAddress(member.getAddress());

      Order order = Order.createOrder(delivery, member, orderItem1 , orderItem2); // ... 문법이 중요함 , 여러 OrderItem들을 배열로 가변적으로 넘길 수 있음
      em.persist(order);//OrderItem , Delivery 전부 cascade 걸려있으니 연계적으로 Crud 될 것임
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
      em.persist(book1);//x 회원이 A아이템을 A1 주문하고 , B 아이템을 B1 주문함 , y회원이 C아이템을 C1 주문하고, D아이템을 D1 주문함

      Book book2 = new Book();
      book2.setName("SPRING BOOK 2");
      book2.setPrice(35000);
      book2.setStockQuantity(90);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 80000, 4);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 70000, 2);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());


      Order order = Order.createOrder(delivery, member, orderItem1 , orderItem2);
      em.persist(order);//OrderItem , Delivery 전부 cascade 걸려있으니 연계적으로 Crud 될 것임
    }



    public void dbInit3(){
      //한 회원이 JPA BOOK1,2를 주문했음 ㅇㅇ
      Member member = new Member();
      member.setName("memberC");
      member.setAddress(new Address("cityC" , "distanceC" , "zipCodeC"));
      em.persist(member);

      Book book1 = new Book();
      book1.setName("KAFKA BOOK 1");
      book1.setPrice(30000);
      book1.setStockQuantity(60);
      em.persist(book1);//x 회원이 A아이템을 A1 주문하고 , B 아이템을 B1 주문함 , y회원이 C아이템을 C1 주문하고, D아이템을 D1 주문함

      Book book2 = new Book();
      book2.setName("KAFKA BOOK 2");
      book2.setPrice(75000);
      book2.setStockQuantity(100);
      em.persist(book2);

      Book book3 = new Book();
      book3.setName("KAFKA BOOK 3");
      book3.setPrice(90000);
      book3.setStockQuantity(90);
      em.persist(book3);




      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 240000, 8);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 450000, 6);
      OrderItem orderItem3 = OrderItem.createOrderItem(book3, 900000, 10);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());


      Order order = Order.createOrder(delivery, member, orderItem1 , orderItem2 , orderItem3);
      em.persist(order);//OrderItem , Delivery 전부 cascade 걸려있으니 연계적으로 Crud 될 것임
    }







  }

}
