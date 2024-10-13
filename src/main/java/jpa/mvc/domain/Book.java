package jpa.mvc.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Book extends Item{

    private String author;
    private String isbn;

    public Book(Long id , String name , int price , int stockQuantity , String author, String isbn) {
        super(id , name , price , stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public Book(){}


    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.setAuthor(author);
        this.setIsbn(isbn);
    }
}
