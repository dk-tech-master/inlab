package kr.inlab.www.repository;

import java.util.List;

import kr.inlab.www.dto.response.ResponseQuestionTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.QuestionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {

    @Query("SELECT new kr.inlab.www.dto.response.ResponseQuestionTypeDto(p.positionId, p.positionName, qt.questionTypeId,qt.questionTypeName,COUNT(q) ) " +
            "FROM QuestionType qt join qt.position p LEFT JOIN qt.questionList q " +
            "WHERE (:name is null or qt.questionTypeName LIKE CONCAT('%', :name, '%'))  " +
            "AND (:positionId is null or p.positionId = :positionId) " +
            "GROUP BY qt.questionTypeId, qt.questionTypeName")
    Page<ResponseQuestionTypeDto> getQuestionTypeList(@Param("name") String name, @Param("positionId") Integer positionId,
                                                      Pageable pageable);

    boolean existsByPosition_PositionIdAndQuestionTypeName(Integer position_positionId, String questionTypeName);

    List<QuestionType> findAllByPosition_PositionId(Integer positionId);
}
