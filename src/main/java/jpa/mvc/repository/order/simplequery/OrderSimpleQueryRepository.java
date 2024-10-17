package jpa.mvc.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import jpa.mvc.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

  private final EntityManager em;

  public List<OrderSimpleQueryDto> findOrderDtos() {
    return em.createQuery("select new jpa.mvc.repository.OrderSimpleQueryDto(o.id , m.name , o.orderDate ,"
        + " o.orderStatus , d.address)"
        + "from Order o "
        + "join o.member m "
        + "join o.delivery d" , OrderSimpleQueryDto.class).getResultList();
  }

}
