package kr.inlab.www.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.RelatedQuestion;

public interface RelatedQuestionRepository extends JpaRepository<RelatedQuestion, Long> {

	Page<RelatedQuestion> findAllByHeadQuestionQuestionId(Long headQuestionId, Pageable pageable);

	boolean existsByHeadQuestionAndTailQuestion(Question headQuestion, Question tailQuestion);
}
