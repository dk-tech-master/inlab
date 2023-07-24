package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestUpdateInterviewQuestionResultDto;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.InterviewQuestionResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewQuestionResultServiceImpl implements InterviewQuestionResultService {

    private final InterviewQuestionResultRepository interviewQuestionResultRepository;
    private final CommentService commentService;
    private final ChecklistResultService checklistResultService;

    @Override
    public InterviewQuestionResult createInterviewQuestionResult(InterviewQuestion interviewQuestion, InterviewResult interviewResult) {
        return interviewQuestionResultRepository.save(InterviewQuestionResult.builder()
                .interviewQuestion(interviewQuestion)
                .interviewResult(interviewResult)
                .build());
    }

    @Override
    public void updateInterviewQuestionResult(RequestUpdateInterviewQuestionResultDto requestDto) {
        commentService.updateComment(requestDto.getCommentDto().getCommentId(), requestDto.getCommentDto());
        checklistResultService.updateChecklistResult(requestDto.getChecklistResultDtoList());
    }
}
