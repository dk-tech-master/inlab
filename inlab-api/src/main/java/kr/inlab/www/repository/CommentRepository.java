package kr.inlab.www.repository;

import kr.inlab.www.entity.Comment;
import kr.inlab.www.entity.InterviewQuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByInterviewQuestionResult(InterviewQuestionResult interviewQuestionResult);
}
