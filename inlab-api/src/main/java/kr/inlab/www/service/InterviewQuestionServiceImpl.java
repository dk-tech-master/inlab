package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewAlreadyExistsException;
import kr.inlab.www.common.exception.InterviewNotFoundException;
import kr.inlab.www.common.exception.InterviewQuestionNotFoundException;
import kr.inlab.www.common.exception.QuestionNotFoundException;
import kr.inlab.www.dto.request.RequestCreateInterviewQuestionDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionDto;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.QuestionVersion;
import kr.inlab.www.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewQuestionServiceImpl implements InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;
    private final InterviewQuestionQueryRepository interviewQuestionQueryRepository;
    private final InterviewRepository interviewRepository;
    private final QuestionRepository questionRepository;
    private final QuestionVersionRepository questionVersionRepository;

    @Override
    public InterviewQuestion getInterviewQuestion(Long interviewQuestionId) {
        return interviewQuestionRepository.findById(interviewQuestionId)
                .orElseThrow(InterviewQuestionNotFoundException::new);
    }

    @Override
    @Transactional
    public void createInterviewQuestion(RequestCreateInterviewQuestionDto requestDto) {
        if(interviewQuestionQueryRepository.interviewQuestionExist(requestDto.getInterviewId(), requestDto.getQuestionId()))
            throw new InterviewAlreadyExistsException();

        Interview interview = interviewRepository.findById(requestDto.getInterviewId())
                .orElseThrow(InterviewNotFoundException::new);
        Question question = questionRepository.findById(requestDto.getQuestionId())
                .orElseThrow(QuestionNotFoundException::new);
        QuestionVersion questionVersion = questionVersionRepository.findById(requestDto.getQuestionVersionId())
                .orElseThrow(QuestionNotFoundException::new);

        InterviewQuestion interviewQuestion = InterviewQuestion.builder()
                .question(question)
                .interview(interview)
                .questionVersion(questionVersion)
                .build();

        interviewQuestionRepository.save(interviewQuestion);
    }

    @Override
    public List<ResponseInterviewQuestionDto> getInterviewQuestionList(Long interviewId) {
        return interviewQuestionQueryRepository.getInterviewQuestionList(interviewId);
    }

    @Transactional
    public void deleteInterviewQuestion(Long interviewQuestionId) {
        InterviewQuestion interviewQuestion = getInterviewQuestion(interviewQuestionId);
        interviewQuestionRepository.delete(interviewQuestion);
    }
}
