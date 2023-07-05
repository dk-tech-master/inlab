package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewQuestionNotFoundException;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewQuestionServiceImpl implements InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;

    @Override
    public InterviewQuestion getInterviewQuestion(Long interviewQuestionId) {
        return interviewQuestionRepository.findById(interviewQuestionId)
                .orElseThrow(InterviewQuestionNotFoundException::new);
    }
}
