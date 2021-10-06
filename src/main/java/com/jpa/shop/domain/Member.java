package com.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class     Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // jpa 내장 타입을 이용한다는 것, Address에서 embeddable어노테이션 준거 빼도 되지만 그냥 둘다써주긔
    private Address address;

    @OneToMany(mappedBy = "member") // 하나의 회원이 여러개의 물건을 구매할 수 있다. & 난 연관관계 주인아냐 mappedBy & order의 member
    private List<Order> orders = new ArrayList<>();
}
