package jpa.mvc.redis;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport , Long> { }//굳이 작성할 필요가 없어지네 어차피 JpaRepository 구현했으니 CRUD걍 서비스에서 호출만 하면 알아서 쓸 수 있음 ㅇㅇ
