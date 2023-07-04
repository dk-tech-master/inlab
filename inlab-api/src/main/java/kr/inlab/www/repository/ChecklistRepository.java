package kr.inlab.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.Checklist;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
}
