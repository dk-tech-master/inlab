package kr.inlab.www.repository;

import kr.inlab.www.entity.ChecklistResult;
import kr.inlab.www.entity.InterviewQuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistResultRepository extends JpaRepository<ChecklistResult, Long> {

    List<ChecklistResult> findAllByInterviewQuestionResult(InterviewQuestionResult interviewQuestionResult);
}
