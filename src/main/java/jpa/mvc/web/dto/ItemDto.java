package jpa.mvc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ItemDto {

    private Long id; //상품 수정을 위한 id값

    @NotBlank
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int stockQuantity;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    public ItemDto() {
    }

    public ItemDto(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }


    public ItemDto(Long id , String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }



    public ItemDto(Long id  , String name, int price, int stockQuantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
