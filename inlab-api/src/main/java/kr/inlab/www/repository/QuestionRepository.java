package kr.inlab.www.repository;

import java.util.List;

import kr.inlab.www.entity.Position;
import kr.inlab.www.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.inlab.www.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Long countByPosition(Position position);

    boolean existsByPosition_PositionId(Integer positionId);

    boolean existsByQuestionType_QuestionTypeId(Integer questionTypeId);


}
