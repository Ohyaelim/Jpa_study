package com.jpa.shop.service;

import com.jpa.shop.domain.Member;
import com.jpa.shop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 서비스 어노테이션을 주면 자동으로 컴포넌트 스캔 대상이 되어서 스프링 빈에 등록된다.
// JPA 데이터 변경이나 모든 로직들은 가급적 transaction 안에서 다 실행되어야 한다.
// 롬복에 AllArgsConstructor가 있다. 근데 @RequiredArgsConstructor 얘는 final 붙은 애만 constructor 만들어준다. 최고
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    /**
     * @Autowired쓰면 스프링이 스프링 빈에 등록되어있는 레포지토리를 injection해줌.(field injection) 하지만 더 좋은 방법도 있다.
     *      단점. 바꿀 수 없다. -> setter injection을 쓴다.
     *
     *      @Autowired
     *         public void setMemberRepository(MemberRepository memberRepository) {
     *             this.memberRepository = memberRepository;
     *         }
     *      test 코드 직성할 때 주입하기 좋아.
     *      단점 : 개발하는 중간에 set으로 바꿀 수 도 있어.
     *
     *      최종적으로 추천하는 것!! - 생성자 injection
     *      최신버전 스프링에서는 생성자 하나면 autowired 어노테이션 없어도 자동으로 injection해준다.
     *
     */
    // 이제 변경할 일 없기 때문에 final로 하는 것을 권장
            // final로 하면 생성자에서 안넣어준게 있을 때 오류로 값세팅 필요성을 알려줌
    private final MemberRepository memberRepository;

    // 롬복의 어노테이션 @AllArgsConstructor이게 얘를 자동으로 만들어준다. @RequiredArgsConstructor는 final 붙은 애의 생성자만~!
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


    /**
     * 회원 가입
     * 쓰기니까 readonly아니라서 별도로 어노테이션 지정
     */
    @Transactional
    public Long join(Member member) {
        // 중복 회원 검증 - 기본적인 validation
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // WAS가 여러개가 떠, memberA가 동시에 insert하면 동시에 가입이 됨.
    // 실무에서는 멀티쓰레드를 고려해 최후의 방어를 해줘서 member의 네임을 유니크 제약 조건으로 잡아주는걸 권장
    private void validateDuplicateMember(Member member) {
        // Exception
        // 같은 이름을 가진 사람이 있는지 찾아본다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    // @Transactional(readOnly = true)라고 주면 jpa 조회에 최적화 해준다.
    // 따라서 읽기에는 readOnly true를 넣어주자
    // 읽기가 아닌경우엔 놉. 데이터 변경이 안된다.
//    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 한 명만 조회
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
