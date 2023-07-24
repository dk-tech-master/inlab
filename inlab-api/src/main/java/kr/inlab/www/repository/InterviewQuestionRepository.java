package kr.inlab.www.repository;

import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findAllByInterview(Interview interview);
}
