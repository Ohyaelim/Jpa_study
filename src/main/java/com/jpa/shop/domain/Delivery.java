package com.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded // 내장 타입이용
    private Address address;

    @Enumerated(EnumType.STRING) // 꼭 넣어줘야하는데, ordinary 타입은 숫자기 때문에 string으로 해주자 (다른 상태가 생겨도 숫자 변화 없게)
    private DeliveryStatus status; //Ready, COMP
}
