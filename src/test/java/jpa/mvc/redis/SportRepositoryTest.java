package jpa.mvc.redis;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.constraints.Size.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@SpringBootTest
class SportRepositoryTest {

  @Autowired private SportRepository sportRepository;

  @Test
  @DisplayName("테스트")
  void aa() {
    Sport sport = new Sport("축구");
    sportRepository.save(sport);

    Sport findSport = sportRepository.findById(sport.getId()).orElse(null);
    Assertions.assertThat(sport).isEqualTo(findSport);

  }
}