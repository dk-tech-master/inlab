package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.InterviewResultDto;
import kr.inlab.www.entity.InterviewAnswer;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.InterviewResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewResultServiceImpl implements InterviewResultService {

    private final InterviewResultRepository interviewResultRepository;
    private final InterviewQuestionService interviewQuestionService;
    private final ChecklistResultService checklistResultService;
    private final InterviewAnswerService interviewAnswerService;
    private final CommentService commentService;
    private final GptCommentService gptCommentService;

    @Transactional
    @Override
    public void createInterviewResult(RequestCreateInterviewResultDto requestDto) {
        InterviewResult interviewResult = interviewResultRepository.save(requestDto.toInterviewResultEntity());

        List<InterviewResultDto> interviewResultDtos = requestDto.getInterviewResultDtos();
        interviewResultDtos.stream().forEach(interviewResultDto -> {
            InterviewQuestion interviewQuestion = interviewQuestionService.getInterviewQuestion(interviewResultDto.getInterviewQuestionId());
            commentService.createComment(interviewQuestion, interviewResult, interviewResultDto.getCommentDto().getContent());
            InterviewAnswer interviewAnswer = interviewAnswerService.createInterviewAnswer(interviewQuestion, interviewResult, interviewResultDto.getInterviewAnswerDto().getContent());
            gptCommentService.createGptComment(interviewQuestion, interviewResult, interviewAnswer.getContent());
            checklistResultService.createChecklistResult(interviewQuestion, interviewResult, interviewResultDto.getChecklistResultDtos());
        });
    }
}
