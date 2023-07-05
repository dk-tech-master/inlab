package kr.inlab.www.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.QuestionVersion;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

	List<Checklist> findAllByQuestionVersion(QuestionVersion questionVersion);
}
