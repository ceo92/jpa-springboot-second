package jpa.mvc.repository;

import jakarta.persistence.EntityManager;
import jpa.mvc.domain.Order;
import jpa.mvc.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    //주문 완료돼서 내부적으로 저장될 때
    public void save(Order order){
        em.persist(order);
    }

    //주문 목록에서 하나만 가져올 때
    public Optional<Order> findById(Long id){
        Order findOrder = em.find(Order.class, id);
        return Optional.ofNullable(findOrder);
    }

    //
    public List<Order> findAll(OrderSearch orderSearch){
        //순수 jpql 동적쿼리 작성
        String username = orderSearch.getUsername();
        OrderStatus orderStatus = orderSearch.getOrderStatus();

        //Order와 겹치는 Member 조인
        //OrderStatus는 Order의 필드이지만 username은 Member의 필드이므로 주문에 대한 회원 조회하려면 Order와 Member을 조인해야됨
        //1. status와 username이 일치하는 경우
        List<Order> resultList;
        if (StringUtils.hasText(username) && (orderStatus==OrderStatus.ORDER || orderStatus ==OrderStatus.CANCEL) ){
            resultList = em.createQuery("select o from Order o join o.member m where o.status = :status and m.username like :username ", Order.class)
                    .setParameter("status", orderStatus)
                    .setParameter("username", username)
                    .getResultList();

        }
        else if (!StringUtils.hasText(username)&& (orderStatus==OrderStatus.ORDER || orderStatus ==OrderStatus.CANCEL) ){
            resultList = em.createQuery("select o from Order o join o.member m where o.status = :status", Order.class)
                    .setParameter("status", orderStatus)
                    .getResultList();
        }
        else if (StringUtils.hasText(username)){
            resultList = em.createQuery("select o from Order o join o.member m where m.username like :username", Order.class)
                    .setParameter("username", username)
                    .getResultList();
        }
        else{
            resultList = em.createQuery("select o from Order o inner join o.member m" , Order.class).getResultList();
        }
        return resultList;



    }



}
