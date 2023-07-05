package kr.inlab.www.service;

import kr.inlab.www.entity.InterviewAnswer;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;
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
    public InterviewAnswer createInterviewAnswer(InterviewQuestion interviewQuestion, InterviewResult interviewResult, String content) {
        InterviewAnswer interviewAnswer = InterviewAnswer.builder()
                .content(content)
                .interviewQuestion(interviewQuestion)
                .interviewResult(interviewResult)
                .build();

        return interviewAnswerRepository.save(interviewAnswer);
    }
}
