package jpa.mvc.domain;

import jakarta.persistence.*;
import jpa.mvc.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue //auto : h2이면 그냥 SEQUENCE 전략 ! sequence 객체로부터 하나씩 만듬 ㅇㅇ
    @Column(name = "DELIVERY_ID")
    private Long id;

    @Embedded
    private Address address;


    //ORDINAL이면 enum 타입 순서에 따라 1, 2, 3 이렇게 들어감 , 근데  중간에 enum에 들어오면 숫자 다 밀림 그러니 이름 기준인 string 쓰란 것!
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;


    @OneToOne(mappedBy = "delivery" , fetch = FetchType.LAZY)
    private Order order;




}
