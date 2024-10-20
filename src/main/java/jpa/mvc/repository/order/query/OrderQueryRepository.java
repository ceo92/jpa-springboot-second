package jpa.mvc.repository.order.query;


import jakarta.persistence.EntityManager;
import java.util.List;
import jpa.mvc.repository.OrderQueryDto;
import jpa.mvc.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

  private final EntityManager em;

  public List<OrderQueryDto> findOrderQueryDtoWithOrderItems() {
    return em.createQuery("select new jpa.mvc.repository.OrderQueryDto(o.id , m.name , o.orderDate ,"
        + " o.orderStatus , d.address)"
        + "from Order o "
        + "join o.member m "
        + "join o.delivery d" , OrderQueryDto.class).getResultList();
  }

}
