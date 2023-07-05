package kr.inlab.www.repository;

import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.QuestionVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findAllByQuestionVersion(QuestionVersion questionVersion);
}
