package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewAlreadyExistsException;
import kr.inlab.www.common.exception.InterviewNotFoundException;
import kr.inlab.www.common.exception.InterviewTitleDuplicateException;
import kr.inlab.www.common.exception.UserNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionnaireDto;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.InterviewQueryRepository;
import kr.inlab.www.repository.InterviewQuestionQueryRepository;
import kr.inlab.www.repository.InterviewRepository;
import kr.inlab.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{

    private final InterviewQuestionQueryRepository interviewQuestionQueryRepository;
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final InterviewQueryRepository interviewQueryRepository;

    @Transactional
    @Override
    public void createInterview(RequestCreateInterviewDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        if(interviewRepository.existsByTitle(requestDto.getInterviewTitle()))
            throw new InterviewAlreadyExistsException();

        Interview interview = Interview.builder()
                .title(requestDto.getInterviewTitle())
                .user(user)
                .build();

        interviewRepository.save(interview);
    }

    @Override
    public ResponseListDto<ResponseInterviewDto> getInterview(Long userId, RequestGetInterviewDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), Sort.Direction.DESC, "interviewId");
        Page<ResponseInterviewDto> interviewList = interviewQueryRepository.getInterview(userId, requestDto, pageable);
        PagingUtil pagingUtil = new PagingUtil(interviewList.getTotalElements(), interviewList.getTotalPages(), interviewList.getNumber(), interviewList.getSize());

        return new ResponseListDto<>(interviewList.getContent(), pagingUtil);
    }

    @Override
    @Transactional
    public void putInterview(Long interviewId ,RequestCreateInterviewDto requestDto) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(InterviewNotFoundException::new);

        if(interviewRepository.existsByTitleAndInterviewIdNot(requestDto.getInterviewTitle(),interviewId)){
            throw new InterviewTitleDuplicateException();}

        interview.updateTitle(requestDto.getInterviewTitle());
    }

    @Override
    public List<ResponseInterviewQuestionnaireDto> getInterviewQuestionnaire(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(InterviewNotFoundException::new);
        return interviewQuestionQueryRepository.getInterviewQuestionnaire(interview);
    }
}
