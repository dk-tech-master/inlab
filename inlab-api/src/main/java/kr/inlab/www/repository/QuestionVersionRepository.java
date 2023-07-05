package kr.inlab.www.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.QuestionVersion;

public interface QuestionVersionRepository extends JpaRepository<QuestionVersion, Long> {
	Optional<QuestionVersion> findByQuestionQuestionId(Long questionId);
}
