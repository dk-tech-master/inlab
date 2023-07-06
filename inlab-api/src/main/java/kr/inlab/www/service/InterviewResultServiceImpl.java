package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewNotFoundException;
import kr.inlab.www.common.exception.InterviewQuestionNotFoundException;
import kr.inlab.www.common.exception.InterviewResultNotFoundException;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.dto.response.*;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.InterviewQuestionRepository;
import kr.inlab.www.repository.InterviewQuestionResultRepository;
import kr.inlab.www.repository.InterviewRepository;
import kr.inlab.www.repository.InterviewResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewResultServiceImpl implements InterviewResultService {

    private final InterviewRepository interviewRepository;
    private final InterviewResultRepository interviewResultRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final InterviewQuestionResultRepository interviewQuestionResultRepository;

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

    @Override
    public ResponseInterviewResultDto getInterviewResult(Long interviewResultId) {
        InterviewResult interviewResult = interviewResultRepository.findById(interviewResultId)
                .orElseThrow(InterviewResultNotFoundException::new);
        Interview interview = interviewResult.getInterview();

        List<InterviewQuestion> interviewQuestions = interviewQuestionRepository.findAllByInterview(interview);
        List<ResponseInterviewQuestionResultDto> responseInterviewQuestionResultDtoList = interviewQuestions.stream().map(interviewQuestion -> {
            InterviewQuestionResult interviewQuestionResult =
                    interviewQuestionResultRepository.findByInterviewQuestionAndInterviewResult(interviewQuestion, interviewResult);

            ResponseInterviewAnswerDto responseInterviewAnswerDto =
                    interviewAnswerService.getInterviewAnswer(interviewQuestionResult);
            ResponseCommentDto responseCommentDto =
                    commentService.getComment(interviewQuestionResult);
            ResponseGptCommentIdDto responseGptCommentIdDto =
                    gptCommentService.getGptCommentId(interviewQuestionResult);
            List<ResponseChecklistDto> responseChecklistDtoList =
                    checklistResultService.getChecklistResultList(interviewQuestionResult);

            return ResponseInterviewQuestionResultDto.builder()
                    .interviewQuestionTitle(interviewQuestion.getQuestionVersion().getTitle())
                    .responseInterviewAnswerDto(responseInterviewAnswerDto)
                    .responseCommentDto(responseCommentDto)
                    .responseGptCommentIdDto(responseGptCommentIdDto)
                    .responseChecklistDtoList(responseChecklistDtoList)
                    .build();
        }).collect(Collectors.toList());

        return ResponseInterviewResultDto.builder()
                .interviewTitle(interview.getTitle())
                .intervieweeName(interviewResult.getIntervieweeName())
                .responseInterviewQuestionResultDtoList(responseInterviewQuestionResultDtoList)
                .build();
    }
}
