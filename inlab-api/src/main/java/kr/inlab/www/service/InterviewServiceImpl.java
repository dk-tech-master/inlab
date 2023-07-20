package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewAlreadyExistsException;
import kr.inlab.www.common.exception.InterviewNotFoundException;
import kr.inlab.www.common.exception.InterviewTitleDuplicateException;
import kr.inlab.www.common.exception.UserNotFoundException;
import kr.inlab.www.common.type.InterviewQuestionStatus;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ChecklistDto;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionStartDto;
import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static kr.inlab.www.dto.response.ResponseInterviewQuestionStartDto.*;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{

    private final InterviewQuestionQueryRepository interviewQuestionQueryRepository;
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final InterviewQueryRepository interviewQueryRepository;
    private final ChecklistRepository checklistRepository;

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
    public ResponseGetInterviewDto getInterview(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(InterviewNotFoundException::new);

        return ResponseGetInterviewDto
            .builder()
            .interviewId(interview.getInterviewId())
            .interviewerNickname(interview.getUser().getNickname())
            .interviewTitle(interview.getTitle())
            .questionCount((int) interview.getQuestions().stream().filter( interviewQuestion -> interviewQuestion.getInterviewQuestionStatus().equals(
                InterviewQuestionStatus.ACTIVE)).count())
            .createdAt(interview.getCreatedAt())
            .build();
    }

    @Override
    public ResponseListDto<ResponseInterviewDto> getInterviews(Long userId, RequestGetInterviewDto requestDto) {
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
    public List<ResponseInterviewQuestionStartDto> getInterviewQuestionStartList(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(InterviewNotFoundException::new);

        List<InterviewQuestionStartDto> interviewQuestionStartDtoList = interviewQuestionQueryRepository.getInterviewQuestionnaire(interview);

        List<ResponseInterviewQuestionStartDto> responseDto = interviewQuestionStartDtoList.stream().map(interviewQuestion -> {
            List<ChecklistDto> checklistDto = checklistRepository.getChecklistByQuestionVersion(interviewQuestion.getQuestionVersionId());

            return ResponseInterviewQuestionStartDto.builder()
                    .interviewQuestionId(interviewQuestion.getInterviewQuestionId())
                    .interviewTitle(interviewQuestion.getInterviewTitle())
                    .questionVersionId(interviewQuestion.getQuestionVersionId())
                    .questionTitle(interviewQuestion.getQuestionTitle())
                    .version(interviewQuestion.getVersion())
                    .checklistList(checklistDto)
                    .build();
        }).collect(Collectors.toList());

        return responseDto;
    }


}
