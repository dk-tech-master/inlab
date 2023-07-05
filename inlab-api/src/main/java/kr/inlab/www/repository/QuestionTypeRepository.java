package kr.inlab.www.repository;

import kr.inlab.www.dto.response.ResponseQuestionTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.QuestionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {

    @Query("SELECT new kr.inlab.www.dto.response.ResponseQuestionTypeDto(qt.questionTypeId,qt.questionTypeName,COUNT(q) ) " +
            "FROM QuestionType qt LEFT JOIN qt.questionList q " +
            "WHERE qt.questionTypeName LIKE CONCAT('%', :name, '%') " +
            "GROUP BY qt.questionTypeId, qt.questionTypeName")
    Page<ResponseQuestionTypeDto> getQuestionTypeList(@Param("name") String name, Pageable pageable);

    boolean existsByQuestionTypeName(String name);
}
