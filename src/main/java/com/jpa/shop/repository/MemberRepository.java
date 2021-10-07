package com.jpa.shop.repository;

import com.jpa.shop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 레포지토리 어노테이션을 사용하면 컴포넌트 스캔에 의해서 자동으로 스프링빈으로 관리
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // JPA를 사용하므로 JPA의 표준 어노테이션 사용
    // 스프링이 entity manager 만들어서 injection해줌
//    @PersistenceContext
    // 저 표준 어노테이션을 꼭 써야 em을 넣어주지만 (스프링부트) 스프링 data jpa가 autowired 지원해줌
//    @Autowired
    private final EntityManager em;

//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public void save(Member member) {
        // em이 저장하는 로직, 영속성에 넣어
        // 트랜젝션이 커밋되는 시점에 DB에 반영
       em.persist(member);
    }

    public Member findOne(Long id) {
        // 타입, pk
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        // JPA 쿼리를 작성해야해 - sql과 기능적으론 동일, sql은 테이블 대상, 얜 엔티티 대상 (JPQL문법)
//        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//        return result;
        // ctrl + alt + n 으로 합치기 [인라인변수]

        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        // :name은 name 파라미터 바인딩딩
       return em.createQuery("select m from Member m where m.name = :name", Member.class)
               .setParameter("name", name)
               .getResultList();
    }
}
