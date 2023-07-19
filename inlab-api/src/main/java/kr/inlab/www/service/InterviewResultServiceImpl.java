package kr.inlab.www.service;

import kr.inlab.www.common.exception.InterviewNotFoundException;
import kr.inlab.www.common.exception.InterviewQuestionNotFoundException;
import kr.inlab.www.common.exception.InterviewResultNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.dto.request.RequestInterviewResultListDto;
import kr.inlab.www.dto.response.*;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.InterviewQuestionRepository;
import kr.inlab.www.repository.InterviewQuestionResultRepository;
import kr.inlab.www.repository.InterviewRepository;
import kr.inlab.www.repository.InterviewResultRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewResultServiceImpl implements InterviewResultService {

    private final InterviewRepository interviewRepository;
    private final InterviewResultRepository interviewResultRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final InterviewQuestionResultRepository interviewQuestionResultRepository;

    private final InterviewQuestionResultService interviewQuestionResultService;
    private final InterviewAnswerService interviewAnswerService;
    private final CommentService commentService;
    private final GptCommentService gptCommentService;
    private final ChecklistResultService checklistResultService;

    @Transactional
    @Override
    public void createInterviewResult(RequestCreateInterviewResultDto requestDto) {
        Interview interview = interviewRepository.findById(requestDto.getInterviewId())
                .orElseThrow(InterviewNotFoundException::new);

        InterviewResult interviewResult = InterviewResult.builder()
                .intervieweeName(requestDto.getIntervieweeName())
                .interview(interview)
                .build();
        interviewResultRepository.save(interviewResult);

        requestDto.getInterviewQuestionResultDtoList().stream().forEach(interviewQuestionResultDto -> {
            InterviewQuestion interviewQuestion = interviewQuestionRepository.findById(interviewQuestionResultDto.getInterviewQuestionId())
                    .orElseThrow(InterviewQuestionNotFoundException::new);

            InterviewQuestionResult interviewQuestionResult =
                    interviewQuestionResultService.createInterviewQuestionResult(interviewQuestion, interviewResult);

            interviewAnswerService.createInterviewAnswer(interviewQuestionResult, interviewQuestionResultDto.getInterviewAnswerDto().getContent());
            commentService.createComment(interviewQuestionResult, interviewQuestionResultDto.getCommentDto().getContent());
            gptCommentService.createGptComment(interviewQuestion, interviewQuestionResult, interviewQuestionResultDto.getInterviewAnswerDto().getContent());
            checklistResultService.createChecklistResult(interviewQuestionResult, interviewQuestionResultDto.getChecklistResultDtoList());
        });
    }

    @Override
    public ResponseInterviewResultDto getInterviewResult(Long interviewResultId) {
        InterviewResult interviewResult = interviewResultRepository.findById(interviewResultId)
                .orElseThrow(InterviewResultNotFoundException::new);
        Interview interview = interviewResult.getInterview();

        List<InterviewQuestion> interviewQuestions = interviewQuestionRepository.findAllByInterview(interview);
        List<ResponseInterviewQuestionResultDto> responseInterviewQuestionResultDtoList = interviewQuestions.stream().map(interviewQuestion -> {
            InterviewQuestionResult interviewQuestionResult =
                    interviewQuestionResultRepository.findByInterviewQuestionAndInterviewResult(interviewQuestion, interviewResult);

            ResponseInterviewAnswerDto responseInterviewAnswerDto =
                    interviewAnswerService.getInterviewAnswer(interviewQuestionResult);
            ResponseCommentDto responseCommentDto =
                    commentService.getComment(interviewQuestionResult);
            ResponseGptCommentDto responseGptCommentDto =
                    gptCommentService.getInterviewResultGptComment(interviewQuestionResult);
            List<ResponseChecklistDto> responseChecklistDtoList =
                    checklistResultService.getChecklistResultList(interviewQuestionResult);

            return ResponseInterviewQuestionResultDto.builder()
                    .interviewQuestionResultId(interviewQuestionResult.getInterviewQuestionResultId())
                    .interviewQuestionTitle(interviewQuestion.getQuestionVersion().getTitle())
                    .responseInterviewAnswerDto(responseInterviewAnswerDto)
                    .responseCommentDto(responseCommentDto)
                    .responseGptCommentDto(responseGptCommentDto)
                    .responseChecklistDtoList(responseChecklistDtoList)
                    .build();
        }).collect(Collectors.toList());

        return ResponseInterviewResultDto.builder()
                .interviewTitle(interview.getTitle())
                .intervieweeName(interviewResult.getIntervieweeName())
                .responseInterviewQuestionResultDtoList(responseInterviewQuestionResultDtoList)
                .build();
    }

    @Override
    public ResponseListDto getInterviewResultList(RequestInterviewResultListDto requestDto) {
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSortDirection(), "interviewResultId");
        Specification<InterviewResult> specification = getInterviewResultListSpecification(requestDto);
        Page<InterviewResult> interviewResultList = interviewResultRepository.findAll(specification, pageRequest);
        PagingUtil pagingUtil = new PagingUtil(interviewResultList.getTotalElements(), interviewResultList.getTotalPages(), interviewResultList.getNumber(), interviewResultList.getSize());

        List<Object> responseDto = interviewResultList.stream()
                .map(InterviewResult::toResponseInterviewResultListDto)
                .collect(Collectors.toList());

        return ResponseListDto.builder()
                .responseList(responseDto)
                .pagingUtil(pagingUtil)
                .build();
    }

    private Specification<InterviewResult> getInterviewResultListSpecification(RequestInterviewResultListDto requestDto) {
        Specification<InterviewResult> specification = Specification.where(null);

        if (Strings.isNotEmpty(requestDto.getIntervieweeName())) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(root.get("intervieweeName"), "%" + requestDto.getIntervieweeName() + "%")
            );
        }
        if (Strings.isNotEmpty(requestDto.getStartDate()) && Strings.isNotEmpty(requestDto.getEndDate())) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.between(
                                    root.get("createdAt"),
                                    LocalDate.parse(requestDto.getStartDate()).atStartOfDay(),
                                    LocalDate.parse(requestDto.getEndDate()).atTime(LocalTime.MAX)
                            )
            );
        }

        return specification;
    }
}
