package jpa.mvc.service;

import jpa.mvc.domain.Member;
import jpa.mvc.exception.DuplicateMemberException;
import jpa.mvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //컴포넌트 스캔 외의 별다른 기능 X , @Repository는 데이터 접근 계층에서 발생한 예외를 DataAccessException으로 바꿔서 서비스 및 상위 계층에 넘겨줌
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        //이름 중복 시 예외 로직
        //validateDuplicateMember(member);
        Long savedId = memberRepository.save(member);
        return savedId;

    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findById(id).orElseThrow();
    }

    private void validateDuplicateMember(Member member) {
        if (!memberRepository.findByName(member.getName()).isEmpty()){
            throw new DuplicateMemberException("이미 존재하는 회원입니다 !");
        }
    }


    @Transactional
    public void update(Long updateId, String name) {
        Member findMember = memberRepository.findById(updateId).orElseThrow();
        findMember.setName(name); //d여속성컨텍스트가 관리하겠지 ㅇㅇ
    }
}
