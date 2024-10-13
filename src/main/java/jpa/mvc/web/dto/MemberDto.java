package jpa.mvc.web.dto;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jpa.mvc.Address;
import jpa.mvc.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String city;

    @NotBlank
    private String distance;

    @NotBlank
    private String zipcode;
    public MemberDto(){}
    public MemberDto(String username, String city, String distance, String zipcode) {
        this.username = username;
        this.city = city;
        this.distance = distance;
        this.zipcode = zipcode;
    }

    public MemberDto(Long id , String username, String city, String distance, String zipcode) {
        this.id=id;
        this.username = username;
        this.city = city;
        this.distance = distance;
        this.zipcode = zipcode;
    }



    /*@Embedded
    private Address address;*/ //애초에 @Entity 없으면 jpa가 인식 못하니까 이것도 못쓰지 않을까? ㅇㅅㅇ
}
