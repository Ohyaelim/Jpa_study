package com.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //jpa 내장 타입이기 때문
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //protected면.. 아... 걍 jpa 스펙상 만든거구나,,, 손대지 말자 느낌
    protected Address(){

    }

    //값이라는것은 변경 되면 안되니까
    //생성할 때만.. 값 세팅 setter를 아예 제공 안함
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
