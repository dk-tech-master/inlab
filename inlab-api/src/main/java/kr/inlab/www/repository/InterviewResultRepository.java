package kr.inlab.www.repository;

import kr.inlab.www.entity.InterviewResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewResultRepository extends JpaRepository<InterviewResult, Long> {
}
