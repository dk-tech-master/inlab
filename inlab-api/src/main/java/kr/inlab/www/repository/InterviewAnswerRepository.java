package kr.inlab.www.repository;

import kr.inlab.www.entity.InterviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswer, Long> {
}
