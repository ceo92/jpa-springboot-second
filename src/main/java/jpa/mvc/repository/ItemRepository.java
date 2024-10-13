package jpa.mvc.repository;


import jakarta.persistence.EntityManager;
import jpa.mvc.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        em.persist(item);
        /*if (item.getId() == null) {
            em.persist(item); //저장 시 DB가 할당해주는 id가 null이면 당연히 db에 아직 내용이 들어가있지 않다는 소리니 처음 해당 상품을 저장하는 것임 그러니 id가 null 이면 em.persist를 통해 새로 저장 하는 것!
        }
        else{ //id가 있으면 이미 db에 상품이 저장되어있단 뜻이니 수정이 되게 함
            em.merge(item); //일부 수정이 안 되고 전체 수정만 가능
        }*/
    }

    public Optional<Item> findById(Long id){
        Item findItem = em.find(Item.class, id);
        return Optional.ofNullable(findItem);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
