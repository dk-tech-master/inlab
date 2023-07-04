package kr.inlab.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.QuestionType;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {
}
