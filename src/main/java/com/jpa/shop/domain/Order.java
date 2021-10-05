package com.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 조인을 무엇으로 할 것인가 fk이름, 연관 관계의 주인
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)  // 원투원일땐 주로 엑세스를 많이하는 쪽이 연관 관계의 주인이 되어야한다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // java8 부터는 하이버네이트가 알어서 로컬데이트타임으로,,,
    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 order, cancel
}
