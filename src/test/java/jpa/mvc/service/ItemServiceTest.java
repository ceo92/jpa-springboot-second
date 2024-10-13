package jpa.mvc.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Arrays;
import jpa.mvc.domain.Book;
import jpa.mvc.domain.Item;
import jpa.mvc.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemServiceTest {

  @Autowired private ItemService itemService;

  //단위 테스트 하려면 ㅅㅂ 무조건 도메인 설계 패턴 적용해야되네 ㅅㅂ ㅋㅋ 그냥 객체만 로컬에서 생성하면 되니 리포지토리를 거치는 테스트 하려면 무조건 EntityManager이 필요하니 해당 객체는 ㅡㅅ프링 부트가 주입해주니
  @Test
  void aa(){
    //given
    Item item = new Book( "AA", 20000 ,50 , "ASDSAD" , "SDFSD");
    itemService.saveItem(item);
    //when
    itemService.addStock(item.getId(), 20);
    //then
    Item findItem = itemService.findOne(item.getId());
    Assertions.assertThat(findItem.getStockQuantity()).isEqualTo(70);


  }

}
