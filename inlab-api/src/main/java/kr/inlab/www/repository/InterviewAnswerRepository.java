package kr.inlab.www.repository;

import kr.inlab.www.entity.InterviewAnswer;
import kr.inlab.www.entity.InterviewQuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswer, Long> {

    Optional<InterviewAnswer> findByInterviewQuestionResult(InterviewQuestionResult interviewQuestionResult);
}
