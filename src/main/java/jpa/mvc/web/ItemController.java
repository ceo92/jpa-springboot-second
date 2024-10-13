package jpa.mvc.web;

import jpa.mvc.domain.Book;
import jpa.mvc.domain.Item;
import jpa.mvc.repository.ItemRepository;
import jpa.mvc.service.ItemService;
import jpa.mvc.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    /**
     * 상품 등록
     */
    @GetMapping("item-form")
    public String getItemForm(Model model) {
        model.addAttribute("item" , new ItemDto());
        return "item/itemForm";
    }

    @PostMapping("item-form")
    public String postItemForm(@Validated @ModelAttribute("item") ItemDto itemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "item/itemForm";
        }
        Long id = itemDto.getId();
        String name = itemDto.getName();
        int price = itemDto.getPrice();
        int stockQuantity = itemDto.getStockQuantity();
        String author = itemDto.getAuthor();
        String isbn = itemDto.getIsbn();
        Book item = new Book( id , name , price , stockQuantity , author , isbn);

        itemService.saveItem(item);
        return "redirect:/";
    }

    @GetMapping("item-list")
    public String getItemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "item/itemList";
    }


    /**
     * 상품 수정
     */
    @GetMapping("item-list/{id}/edit-form")
    public String getEditForm(@PathVariable("id") Long id , Model model){
        Book item = (Book)itemService.findOne(id);
        ItemDto itemDto = new ItemDto(item.getId() , item.getName() , item.getPrice() , item.getStockQuantity() , item.getAuthor() , item.getIsbn());
        model.addAttribute("item", itemDto);
        return "item/editForm";
    }

    @PostMapping("item-list/{id}/edit-form")
    public String postEditForm(@Validated @ModelAttribute("item") ItemDto itemDto , BindingResult bindingResult){
        //오류 잡기
        if (bindingResult.hasErrors()){
            return "item/editForm";
        }

        itemService.updateItem(itemDto.getId() , itemDto.getName() ,itemDto.getPrice() , itemDto.getStockQuantity(),  itemDto.getAuthor() , itemDto.getIsbn());


        return "redirect:/item-list";

    }










}
