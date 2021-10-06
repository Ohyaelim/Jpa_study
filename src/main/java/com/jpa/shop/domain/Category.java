package com.jpa.shop.domain;

import com.jpa.shop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) // 테이블로 매핑해줘야해.. 실무에선 안쓴다. 반대편은 걍 mappedBY해라!
    private List<Item> items = new ArrayList<>();


    // 같은 엔티티에서 셀프로 연관 관계 가능
    @ManyToOne(fetch = FetchType.LAZY) //내 부모니까
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 자식은 여러개 가질 수 있다.
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // === 연관관계 편의 메서드 ===
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}
