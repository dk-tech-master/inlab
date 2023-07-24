package kr.inlab.www.repository;

import kr.inlab.www.entity.GptComment;
import kr.inlab.www.entity.InterviewQuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GptCommentRepository extends JpaRepository<GptComment, Long> {

    Optional<GptComment> findByInterviewQuestionResult(InterviewQuestionResult interviewQuestionResult);
}
