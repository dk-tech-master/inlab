package kr.inlab.www.repository;

import java.util.List;
import kr.inlab.www.dto.response.ResponseQuestionTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.QuestionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {

    @Query("SELECT new kr.inlab.www.dto.response.ResponseQuestionTypeDto(p.positionName, qt.questionTypeId,qt.questionTypeName,COUNT(q) ) " +
            "FROM QuestionType qt join qt.position p LEFT JOIN qt.questionList q " +
            "WHERE (:name is null or qt.questionTypeName LIKE CONCAT('%', :name, '%'))  " +
            "GROUP BY qt.questionTypeId, qt.questionTypeName")
    Page<ResponseQuestionTypeDto> getQuestionTypeList(@Param("name") String name,
        Pageable pageable);

    boolean existsByPositionAndQuestionTypeName(Integer positionId, String name);

    List<QuestionType> findAllByPosition_PositionId(Integer positionId);
}
