package jpa.mvc.service;

import jpa.mvc.domain.Member;
import jpa.mvc.exception.DuplicateMemberException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest //JPA가 DB에까지 돌고 하는 걸 직접 하기위해 단위 테스트가 아닌 통합 테스트
@Transactional //테스트 간 독립성 , 계속 실행 가능성 ㅇㅇ
class MemberServiceTest {
    //JPA를 쓴다면 스프링 컨테이너가 편하게 di해주는 기능을 이용하기 위해 스프링 컨테이너를 띄워야되네

    @Autowired MemberService memberService;

    @Test
    @Rollback(value = false)
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        memberService.join(member);

        //then
        Assertions.assertThat(memberService.findOne(member.getId())).isEqualTo(member);
    }
    //중복 테스트
    @Test
    void 회원가입_중복_확인(){
        //given
        Member member1 = new Member();
        member1.setName("memberA");

        Member member2 = new Member();
        member2.setName("memberA");

        //when
        memberService.join(member1);

        //then
        Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(DuplicateMemberException.class).hasMessage("이미 존재하는 회원입니다 !");
    }





}