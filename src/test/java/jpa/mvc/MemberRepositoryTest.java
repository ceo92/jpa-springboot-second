package jpa.mvc;

import jpa.mvc.domain.Member;
import jpa.mvc.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Rollback(value = false)
    void save() {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        System.out.println("======");
        Long saveId = memberRepository.save(member); //SEQUENCE 전략일 경우 select next value for member_seq로 id만 가져옴
        System.out.println("======");
        Member findMember = memberRepository.findById(saveId).orElseThrow();

        //then
        Assertions.assertThat(findMember).isEqualTo(member); //
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());

    }

    @Test
    void findById() {
    }
}