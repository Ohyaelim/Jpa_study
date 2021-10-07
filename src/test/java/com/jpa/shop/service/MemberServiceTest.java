package com.jpa.shop.service;

import com.jpa.shop.domain.Member;
import com.jpa.shop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

// spring이랑 integration해서 테스트할거임. 아래 두 어노테이션 필수
// 또 데이터 변경해야하기 때문에 transactional 어노테이션 넣어준다. 이게 있어야 rollback된다.
@RunWith(SpringRunner.class) // junit 실행할 때 스프링이랑 엮어서 실행할래!
@SpringBootTest // 스프링 부트를 띄운 상태로 테스트 하려면 필요
@Transactional
public class MemberServiceTest {

    //테스트니까 걍 autowired.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    //롤백이지만 DB에 쿼리 날리는 거 보고싶으면
    @Autowired EntityManager em;


    //insert문을 눈으로 보고 싶으면 @Rollback(false)
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("oh");
        
        //when
        // commit을 해야 insert문 만들어짐
        // 스프링의 transactional은 기본적으로 commit안되고 rollback해버림
        // 스프링이 롤백해버리면 jpa 입장에서는 insert쿼리 날릴 이유 조차 없어. (영속성 컨텍스트 하지 않아)
        Long saveId = memberService.join(member);
        
        //then
        //롤백이지만 DB에 쿼리 날리는 거 보고싶으면.. 영속성 context에 있는 쿼리를 날리고~~롤백
        em.flush();

        // member와 리포지토리에서 저장한 멤버와 같아야해
        // transactional 어노테이션 필수필수필수필수 - 스프링과 JPA를 함께 사용하게되면
        // 영속성 컨텍스트의 생존 범위가 트랜잭션과 동일하게 맞춰지게 된다.
        // 따라서 트랜젝션이 다르면 다른 영속성 컨텍스트를 사용하게 된다. -> 아래가 false로 실패하게 되더라...
        assertEquals(member, memberRepository.findOne(saveId));
    }
    
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("oh");

        Member member2 = new Member();
        member2.setName("oh");
        
        //when
        memberService.join(member1);
        // 같은 이름이니까 예외터져야해
//        try {
//            memberService.join(member2);
//        }catch (IllegalStateException e){
//            return;
//        }
        memberService.join(member2);
        
        //then
        // 여기까지 오기 전에 예외가 발생해야 한다.
        fail("예외가 위에서 발생해야 한다.");
    }

}