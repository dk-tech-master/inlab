package kr.inlab.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.QuestionLevel;

public interface QuestionLevelRepository extends JpaRepository<QuestionLevel, Integer> {
}
