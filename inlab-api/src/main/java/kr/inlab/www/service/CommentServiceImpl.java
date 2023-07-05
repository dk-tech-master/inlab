package kr.inlab.www.service;

import kr.inlab.www.common.exception.CommentNotFoundException;
import kr.inlab.www.entity.Comment;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public void createComment(InterviewQuestion interviewQuestion, InterviewResult interviewResult, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .interviewQuestion(interviewQuestion)
                .interviewResult(interviewResult)
                .build();

        commentRepository.save(comment);
    }
}
