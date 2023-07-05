package kr.inlab.www.repository;

import kr.inlab.www.entity.InterviewQuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuestionResultRepository extends JpaRepository<InterviewQuestionResult, Long> {
}
