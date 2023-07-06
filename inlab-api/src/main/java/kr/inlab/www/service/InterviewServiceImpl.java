package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewAlreadyExistsException;
import kr.inlab.www.common.exception.UserNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.InterviewRepository;
import kr.inlab.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

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
        Page<ResponseInterviewDto> interviewList = interviewRepository.getInterviewList(userId, requestDto.getInterviewTitle(), pageable);
        PagingUtil pagingUtil = new PagingUtil(interviewList.getTotalElements(), interviewList.getTotalPages(), interviewList.getNumber(), interviewList.getSize());

        return new ResponseListDto<>(interviewList.getContent(), pagingUtil);
    }
}
