package kr.inlab.www.service;

import kr.inlab.www.entity.Comment;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void createComment(InterviewQuestionResult interviewQuestionResult, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .interviewQuestionResult(interviewQuestionResult)
                .build();

        commentRepository.save(comment);
    }
}
