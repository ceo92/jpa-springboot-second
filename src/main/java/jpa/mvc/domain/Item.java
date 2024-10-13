package jpa.mvc.domain;

import jakarta.persistence.*;
import jpa.mvc.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item {
    public Item(Long id , String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public Item(){}
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

    @Id @GeneratedValue //auto : h2이면 그냥 SEQUENCE 전략 ! sequence 객체로부터 하나씩 만듬 ㅇㅇ
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * 상품 재고 증가 비즈니스 로직(편의 메서드)
     */
    public void addStock(int stockQuantity){
        this.stockQuantity += stockQuantity;
    }

    /**
     * 상품 재고 감소 비즈니스 로직(편의 메서드)
     */
    public void removeStock(int stockQuantity) {
        int difference = this.stockQuantity - stockQuantity;
        if (difference < 0) {
            throw new NotEnoughStockException("재고가 부족합니다");
        }
        this.stockQuantity = difference;

    }







}
