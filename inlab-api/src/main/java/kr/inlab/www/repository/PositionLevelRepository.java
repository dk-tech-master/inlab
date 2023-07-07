package kr.inlab.www.repository;

import java.util.List;
import kr.inlab.www.entity.PositionLevel;
import kr.inlab.www.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionLevelRepository extends JpaRepository<PositionLevel, Long> {

    List<PositionLevel> findByUser(User user);
}
