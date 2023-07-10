package kr.inlab.www.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.RelatedQuestion;

public interface RelatedQuestionRepository extends JpaRepository<RelatedQuestion, Long> {
	Page<RelatedQuestion> findByHeadQuestionQuestionId(Long headQuestionId, Pageable pageable);
}
