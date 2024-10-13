package jpa.mvc.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jpa.mvc.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member); // 영속성 컨텍스트 1차 캐시 공간에 저장 영속성 컨텍스트의 쓰기 지연을 통해 쓰기 지연 sql 저장소에 sql들 차곡차곡 저장되다가 flush 시점에 한번에 나감 더티 체킹(변경 감지) 해서 1차 캐시에 기존 내역이라 ㅇ비교해서 바뀐 부분 update 쿼리 ㄴql 저장소에 저장해서 flush 시점에 쿼리 날림  변경된 내역
        return member.getId();
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){ //db에서 조회를 해오니 db에 저장된 Member의 특정 name을 where 조건을 통해 조회해오는 것
        return em.createQuery("select m from Member m where m.name = :name" , Member.class)
                .setParameter("name" , name)
                .getResultList();
    }





}
