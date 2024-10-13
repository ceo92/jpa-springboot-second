package jpa.mvc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue //auto : h2이면 그냥 SEQUENCE 전략 ! sequence 객체로부터 하나씩 만듬 ㅇㅇ
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY) //다대다는 객체는 ㄱㅊ은데 데이터베이스는 안되므로 중간 테이블 있어야됨 그래서 임의로 만들어줘야됨
    @JoinTable(name = "CATEGORY_ITEM" ,
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>(); //Item M <=> N Category , Category M <=> N Category

    //다대일 셀프 참조?
    @ManyToOne
    @JoinColumn(name = "PARENT_ID") //외래키를 PARENT_ID라는 이름으로 생성
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();


}
