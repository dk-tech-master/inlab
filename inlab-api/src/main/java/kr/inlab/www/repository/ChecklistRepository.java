package kr.inlab.www.repository;

import kr.inlab.www.dto.common.ChecklistDto;
import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.QuestionVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findAllByQuestionVersion(QuestionVersion questionVersion);

    @Query("select new kr.inlab.www.dto.common.ChecklistDto(cl.checklistId, cl.content) from Checklist cl " +
            "where cl.questionVersion.questionVersionId = :questionVersionId")
    List<ChecklistDto> getChecklistByQuestionVersion(@Param("questionVersionId") Long questionVersionId);
}
