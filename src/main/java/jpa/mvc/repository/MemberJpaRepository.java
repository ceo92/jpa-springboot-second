package jpa.mvc.repository;

import jpa.mvc.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

interface MemberJpaRepository extends JpaRepository<Member, Long> {

  @Override
  <S extends Member> S save(S entity);
}
