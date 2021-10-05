package com.jpa.shop.domain.item;
//구현체를 가지고 할 것이므로 추상 클래스를 만든다.

import com.jpa.shop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) //상속 관계 전략을 잡아야한다. joined는 정규화 된 스타일
@DiscriminatorColumn(name = "DTYPE") // 부모 클래스에 선언한다. 하위 클래스를 구분하는 용도의 컬럼. default = dtype
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


}
