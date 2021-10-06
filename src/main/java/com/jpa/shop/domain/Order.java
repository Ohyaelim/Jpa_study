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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // cascade, order의 orderItems에 데이터를 넣어두고 오더를 저장하면 오더 아이템도 저장된다.
    private List<OrderItem> orderItems = new ArrayList<>();
    // entity당 각각 persist해줘야해서
    // persist(orderItemA) persist(orderItemB) persist(order) 해야하지만
    // cascade힘으로 persist(order)만하면된다. persist를 전파하는 역할
    // all 옵션이니까 delete 할때도 다같이 삭제된다.

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // 원투원일땐 주로 엑세스를 많이하는 쪽이 연관 관계의 주인이 되어야한다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // java8 부터는 하이버네이트가 알어서 로컬데이트타임으로,,,
    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 order, cancel

    // === 연관관계 편의 메서드 ===
    // 이 연관관계 메소드는 핵심적으로 컨트롤하는 쪽이 들고 있는게 좋다.

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//        member.getOrders().add(order);
//        order.setMember(member);
//    }
    // 와 같이 쓰곤했는데 아래 두 줄을 하나로 묶는 것이 등장하게 되었다.
    //

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
