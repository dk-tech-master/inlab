package kr.inlab.www.repository;

import java.util.Optional;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.response.ResponseQuestionVersionsDto;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.QuestionVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionVersionRepository extends JpaRepository<QuestionVersion, Long> {

    Optional<QuestionVersion> findByQuestionQuestionId(Long questionId);

    Optional<QuestionVersion> findTopByQuestionQuestionIdAndIsLatest(Long questionId, YesNo isLatest);




    @Query("SELECT qv FROM QuestionVersion qv WHERE qv.question = :question AND qv.isLatest = :isLatest ORDER BY qv.version DESC")
    Optional<QuestionVersion> findTopByQuestionAndIsLatest(@Param("question") Question question,
        @Param("isLatest") YesNo isLatest);

    @Query(
        "SELECT new kr.inlab.www.dto.response.ResponseQuestionVersionsDto(qv.title, p.positionId, p.positionName, qt.questionTypeId, qt.questionTypeName, ql.questionLevelId, ql.questionLevelName, qv.version) "
            +
            "FROM QuestionVersion qv " +
            "JOIN qv.question q " +
            "JOIN q.position p " +
            "JOIN q.questionType qt " +
            "JOIN qv.questionLevel ql " +
            "WHERE q.questionId = :questionId " +
            "ORDER BY qv.version DESC")
    Page<ResponseQuestionVersionsDto> findQuestionVersions(@Param("questionId") Long questionId, Pageable pageable);
}
