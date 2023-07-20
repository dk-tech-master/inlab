package kr.inlab.www.repository;

import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.entity.InterviewResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewQuestionResultRepository extends JpaRepository<InterviewQuestionResult, Long> {

    InterviewQuestionResult findByInterviewQuestionAndInterviewResult(InterviewQuestion interviewQuestion, InterviewResult interviewResult);

    List<InterviewQuestionResult> findAllByInterviewResult(InterviewResult interviewResult);
}
