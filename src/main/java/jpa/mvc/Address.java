package jpa.mvc;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Embeddable
@Getter @EqualsAndHashCode //equals()  ,hashcode() 오버라이딩 , 값  비교 해야하므로 동등성(==) 비교 및 동일성(값) 비교 둘 다 가능하게끔 ㅇㅇ
public class Address {
    //VO 설계는 equals , hashCode는 컬렉션 사용 시 , 불변 객체로 설계해야됨 , 즉 설정자 x
    private String city;
    private String distance;
    private String zipcode;
    public Address(String city, String distance, String zipcode) { //처음 vo 값 세팅 시 필요
        this.city = city;
        this.distance = distance;
        this.zipcode = zipcode;
    }

    protected Address(){} //기본 생성자 필수





}
