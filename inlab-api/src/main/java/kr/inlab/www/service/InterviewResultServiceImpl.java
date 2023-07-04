package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.ChecklistResultRepository;
import kr.inlab.www.repository.InterviewResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewResultServiceImpl implements InterviewResultService {

    private final InterviewResultRepository interviewResultRepository;
    private final ChecklistResultRepository checklistResultRepository;
//    private final InterviewAnswerRepository interviewAnswerRepository;
//    private final CommentRepository commentRepository;
//    private final GptCommentRepository gptCommentRepository;

    @Transactional
    @Override
    public void createInterviewResult(RequestCreateInterviewResultDto requestDto) {
        InterviewResult interviewResult = interviewResultRepository.save(requestDto.toInterviewResultEntity());


    }
}
