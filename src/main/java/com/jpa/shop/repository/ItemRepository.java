package com.jpa.shop.repository;

import com.jpa.shop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId()==null) {
            // id 값이 없다는건 새로 생성한 객체를 뜻한다.
            em.persist(item);
        } else {
            // 업데이트의 성격이라고 생각하자.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
