package jpa.mvc.service;

import jpa.mvc.domain.Book;
import jpa.mvc.domain.Item;
import jpa.mvc.repository.ItemRepository;
import jpa.mvc.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional //변경이 일어나야하므로 readOnly가 되면 안됨
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //요따구로 쓰면 지저분해짐 dto는 웹에서만 !
   /* @Transactional
    public void updateItem(Long itemId , ItemDto itemDto){
        Book findItem = (Book)findOne(itemId);
        findItem.setName(itemDto.getName());
        findItem.setPrice(itemDto.getPrice());
        findItem.setStockQuantity(itemDto.getStockQuantity());
        findItem.setAuthor(itemDto.getAuthor());
        findItem.setIsbn(itemDto.getIsbn());
    }*/

    @Transactional //crud가 가능하게 readOnly로 안 둔 트랜잭션을 적용해야됨 한 트랜잭션 내에서 변경도 가능하게끔 readOnly는 오직 select 쿼리만 나가고 !
    public void updateItem(Long itemId , String name , int price , int stockQuantity , String author , String isbn){
        Book item = (Book)findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        item.setAuthor(author);
        item.setIsbn(isbn);
    }
    @Transactional
    public void addStock(Long itemId , int stockQuantity){
        Item item = itemRepository.findById(itemId).orElseThrow();
        int result = item.getStockQuantity() + stockQuantity;
        System.out.println("result = " + result);
        item.setStockQuantity(result);
    }


    public void removeStock(Long itemId, int stockQuantity) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        int difference = item.getStockQuantity() - stockQuantity;
        if (difference < 0){
            throw new IllegalStateException("재고 부족!!");
        }
        item.setStockQuantity(difference);
    }




    public Item findOne(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }




}
