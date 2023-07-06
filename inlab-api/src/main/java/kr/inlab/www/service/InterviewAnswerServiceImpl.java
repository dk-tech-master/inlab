package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewAnswerNotFoundException;
import kr.inlab.www.dto.response.ResponseInterviewAnswerDto;
import kr.inlab.www.entity.InterviewAnswer;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.repository.InterviewAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewAnswerServiceImpl implements InterviewAnswerService {

    private final InterviewAnswerRepository interviewAnswerRepository;

    @Transactional
    @Override
    public InterviewAnswer createInterviewAnswer(InterviewQuestionResult interviewQuestionResult, String content) {
        InterviewAnswer interviewAnswer = InterviewAnswer.builder()
                .content(content)
                .interviewQuestionResult(interviewQuestionResult)
                .build();

        return interviewAnswerRepository.save(interviewAnswer);
    }

    @Override
    public ResponseInterviewAnswerDto getInterviewAnswer(InterviewQuestionResult interviewQuestionResult) {
        InterviewAnswer interviewAnswer = interviewAnswerRepository.findByInterviewQuestionResult(interviewQuestionResult)
                .orElseThrow(InterviewAnswerNotFoundException::new);

        return interviewAnswer.toResponseInterviewAnswerDto();
    }
}
