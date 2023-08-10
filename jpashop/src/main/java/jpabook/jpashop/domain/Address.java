package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

// JPA의 내장 타입 => Embeddable 어노테이션
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 인스턴스 함부로 생성되는 것을 막음 -> protected
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
