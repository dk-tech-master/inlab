package kr.inlab.www.repository;

import kr.inlab.www.entity.InterviewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewResultRepository extends JpaRepository<InterviewResult, Long> {

    Page<InterviewResult> findAll(Specification specification, Pageable pageable);
}
