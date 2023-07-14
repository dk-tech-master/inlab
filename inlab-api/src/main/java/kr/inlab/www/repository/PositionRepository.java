package kr.inlab.www.repository;

import kr.inlab.www.dto.response.ResponseGetPositionLevelDto;
import kr.inlab.www.dto.response.ResponsePositionDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    @Query("SELECT new kr.inlab.www.dto.response.ResponsePositionDto(p.positionId, p.positionName, COUNT(q)) " +
            "FROM Position p LEFT JOIN p.questionList q " +
            "WHERE (:name is null or p.positionName LIKE CONCAT('%', :name, '%') ) " +
            "GROUP BY p.positionId, p.positionName")
    Page<ResponsePositionDto> getPositionsList(@Param("name") String name, Pageable pageable);

    boolean existsByPositionName(String name);
}
