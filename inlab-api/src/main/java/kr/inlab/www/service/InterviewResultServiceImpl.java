package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewNotFoundException;
import kr.inlab.www.common.exception.InterviewQuestionNotFoundException;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.InterviewQuestionRepository;
import kr.inlab.www.repository.InterviewRepository;
import kr.inlab.www.repository.InterviewResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewResultServiceImpl implements InterviewResultService {

    private final InterviewRepository interviewRepository;
    private final InterviewResultRepository interviewResultRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;

    private final InterviewQuestionResultService interviewQuestionResultService;
    private final InterviewAnswerService interviewAnswerService;
    private final CommentService commentService;
    private final GptCommentService gptCommentService;
    private final ChecklistResultService checklistResultService;

    @Transactional
    @Override
    public void createInterviewResult(RequestCreateInterviewResultDto requestDto) {
        Interview interview = interviewRepository.findById(requestDto.getInterviewId())
                .orElseThrow(InterviewNotFoundException::new);

        InterviewResult interviewResult = InterviewResult.builder()
                .intervieweeName(requestDto.getIntervieweeName())
                .interview(interview)
                .build();
        interviewResultRepository.save(interviewResult);

        requestDto.getInterviewQuestionResultDtoList().stream().forEach(interviewQuestionResultDto -> {
            InterviewQuestion interviewQuestion = interviewQuestionRepository.findById(interviewQuestionResultDto.getInterviewQuestionId())
                    .orElseThrow(InterviewQuestionNotFoundException::new);

            InterviewQuestionResult interviewQuestionResult =
                    interviewQuestionResultService.createInterviewQuestionResult(interviewQuestion, interviewResult);

            interviewAnswerService.createInterviewAnswer(interviewQuestionResult, interviewQuestionResultDto.getInterviewAnswerDto().getContent());
            commentService.createComment(interviewQuestionResult, interviewQuestionResultDto.getCommentDto().getContent());
            gptCommentService.createGptComment(interviewQuestion, interviewQuestionResult, interviewQuestionResultDto.getInterviewAnswerDto().getContent());
            checklistResultService.createChecklistResult(interviewQuestionResult, interviewQuestionResultDto.getChecklistResultDtoList());
        });
    }
}
