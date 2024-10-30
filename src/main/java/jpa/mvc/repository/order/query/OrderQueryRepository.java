package jpa.mvc.repository.order.query;


import jakarta.persistence.EntityManager;
import java.util.List;
import jpa.mvc.repository.OrderItemQueryDto;
import jpa.mvc.repository.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

  private final EntityManager em;

  //dto에는 연관관계를 정의하면 안 되고 연관관계에서 필요한 데이터들을 정의


  @EntityGraph

  private List<OrderQueryDto> findOrders() {
    return em.createQuery(
        "select new jpa.mvc.repository.OrderQueryDto(o.id , m.name , o.orderDate ,"
            + " o.orderStatus , d.address)"
            + "from Order o "
            + "join o.member m "
            + "join o.delivery d", OrderQueryDto.class).getResultList();
  }




}
