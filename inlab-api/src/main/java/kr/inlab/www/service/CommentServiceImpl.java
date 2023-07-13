package kr.inlab.www.service;

import kr.inlab.www.common.exception.CommentNotFoundException;
import kr.inlab.www.dto.request.RequestUpdateCommentDto;
import kr.inlab.www.dto.response.ResponseCommentDto;
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

    @Override
    public ResponseCommentDto getComment(InterviewQuestionResult interviewQuestionResult) {
        Comment comment = commentRepository.findByInterviewQuestionResult(interviewQuestionResult)
                .orElseThrow(CommentNotFoundException::new);

        return comment.toResponseCommentDto();
    }

    @Override
    @Transactional
    public ResponseCommentDto updateComment(Long commentId, RequestUpdateCommentDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.editComment(requestDto.getContent());

        return comment.toResponseCommentDto();
    }
}
