package kr.inlab.www.repository;

import kr.inlab.www.entity.GptComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GptCommentRepository extends JpaRepository<GptComment, Long> {
}
