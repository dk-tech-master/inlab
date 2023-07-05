package kr.inlab.www.service;

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

    @Override
    public InterviewQuestionResult createInterviewQuestionResult(InterviewQuestion interviewQuestion, InterviewResult interviewResult) {
        return interviewQuestionResultRepository.save(InterviewQuestionResult.builder()
                .interviewQuestion(interviewQuestion)
                .interviewResult(interviewResult)
                .build());
    }
}
