package kr.inlab.www.service;

import kr.inlab.www.common.exception.*;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.RequestListDto;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import kr.inlab.www.dto.response.ResponseQuestionVersionsDto;
import kr.inlab.www.entity.*;
import kr.inlab.www.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionVersionRepository questionVersionRepository;
    private final PositionRepository positionRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final QuestionLevelRepository questionLevelRepository;
    private final ChecklistRepository checklistRepository;
    private final UserRepository userRepository;
    private final UserQuestionHistoryRepository userQuestionHistoryRepository;
    private final QuestionVersionQueryRepository questionVersionQueryRepository;

    private final ChecklistService checklistService;
    private final AuthorityService authorityService;
    private final UserService userService;

    @Transactional
    @Override
    public void createQuestion(RequestCreateQuestionDto requestDto) {
        authorityService.checkUserHasAuthorityToQuestion(requestDto.getPositionId(), requestDto.getQuestionLevelId());

        Position position = positionRepository.findById(requestDto.getPositionId())
                .orElseThrow(PositionNotFoundException::new);
        QuestionType questionType = questionTypeRepository.findById(requestDto.getQuestionTypeId())
                .orElseThrow(QuestionTypeNotFoundException::new);

        Question question = questionRepository.save(Question.builder()
                .position(position)
                .questionType(questionType)
                .build());

        QuestionLevel questionLevel = questionLevelRepository.findById(requestDto.getQuestionLevelId())
                .orElseThrow(QuestionLevelNotFoundException::new);

        Integer version = 1;
        QuestionVersion questionVersion = questionVersionRepository.save(QuestionVersion.builder()
                .title(requestDto.getTitle())
                .version(version)
                .question(question)
                .questionLevel(questionLevel)
                .build());

        checklistService.saveChecklists(requestDto.getChecklists(), questionVersion);
    }

    @Override
    public ResponseGetQuestionDto getQuestion(Long questionId, String username) {
        authorityService.checkUserHasAuthorityToQuestion(questionId);
        QuestionVersion questionVersion = questionVersionRepository.findTopByQuestionQuestionIdAndIsLatest(questionId,
                        YesNo.Y)
                .orElseThrow(QuestionVersionNotFoundException::new);

        List<Checklist> checklistEntities = checklistRepository.findAllByQuestionVersion(questionVersion);
        List<String> checklists = checklistEntities.stream()
                .map(Checklist::getContent)
                .collect(Collectors.toList());

        // 열람이력 저장
        User user = userRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);
        userQuestionHistoryRepository.save(UserQuestionHistory.builder()
                .user(user)
                .question(questionVersion.getQuestion())
                .build());

        return ResponseGetQuestionDto.builder()
                .title(questionVersion.getTitle())
                .questionTypeId(questionVersion.getQuestion().getQuestionType().getQuestionTypeId())
                .questionTypeName(questionVersion.getQuestion().getQuestionType().getQuestionTypeName())
                .positionId(questionVersion.getQuestion().getPosition().getPositionId())
                .positionName(questionVersion.getQuestion().getPosition().getPositionName())
                .questionLevelId(questionVersion.getQuestionLevel().getQuestionLevelId())
                .questionLevelName(questionVersion.getQuestionLevel().getQuestionLevelName())
                .version(questionVersion.getVersion())
                .checklists(checklists)
                .build();
    }

    @Override
    public ResponseListDto<ResponseGetQuestionsDto> getQuestions(RequestQuestionsDto requestDto) {
        if (userService.isAdmin()) {
            return getAllQuestions(requestDto);
        }

        return getQuestionsByAuthority(requestDto);
    }

    @Override
    public ResponseListDto<ResponseQuestionVersionsDto> getQuestionVersions(RequestListDto requestDto,
                                                                            Long questionId) {
        authorityService.checkUserHasAuthorityToQuestion(questionId);

        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(),
                Sort.by(Sort.Direction.DESC, "version"));
        Page<ResponseQuestionVersionsDto> questionVersionList = questionVersionRepository.findQuestionVersions(
                questionId, pageable);
        PagingUtil pagingUtil = new PagingUtil(questionVersionList.getTotalElements(),
                questionVersionList.getTotalPages(), questionVersionList.getNumber(), questionVersionList.getSize());

        return new ResponseListDto<ResponseQuestionVersionsDto>(questionVersionList.getContent(), pagingUtil);
    }

    @Transactional
    @Override
    public void updateQuestion(RequestUpdateQuestionDto requestDto, Long questionId) {
        authorityService.checkUserHasAuthorityToQuestion(questionId);

        Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        QuestionLevel questionLevel = questionLevelRepository.findById(requestDto.getQuestionLevelId())
                .orElseThrow(QuestionLevelNotFoundException::new);
        QuestionVersion latestQuestionVersion = questionVersionRepository.findTopByQuestionAndIsLatest(question,
                        YesNo.Y)
                .orElseThrow(LatestQuestionVersionNotFoundException::new);

        QuestionVersion newQuestionVersion = QuestionVersion.builder()
                .title(requestDto.getTitle())
                .version(latestQuestionVersion.getVersion() + 1)
                .question(question)
                .questionLevel(questionLevel)
                .build();
        QuestionVersion savedQuestionVersion = questionVersionRepository.save(newQuestionVersion);

        checklistService.saveChecklists(requestDto.getChecklists(), savedQuestionVersion);
        latestQuestionVersion.updateIsLatest(YesNo.N);
    }

	private ResponseListDto<ResponseGetQuestionsDto> getAllQuestions(RequestQuestionsDto requestDto) {
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(),
			Sort.by(Sort.Direction.ASC, "questionVersionId"));
		Page<ResponseGetQuestionsDto> questionList = questionVersionQueryRepository.findQuestions(
			Collections.emptyMap(),
			requestDto,
			pageable
		);
		PagingUtil pagingUtil = new PagingUtil(questionList.getTotalElements(), questionList.getTotalPages(),
			questionList.getNumber(), questionList.getSize());

        return new ResponseListDto<ResponseGetQuestionsDto>(questionList.getContent(), pagingUtil);
    }

    private ResponseListDto<ResponseGetQuestionsDto> getQuestionsByAuthority(RequestQuestionsDto requestDto) {
        List<PositionLevel> positionLevels = authorityService.getPositionLevels();
        Map<Integer, List<Integer>> groupedPositionLevels = positionLevels.stream()
                .collect(Collectors.groupingBy(
                        positionLevel -> positionLevel.getPosition().getPositionId(),
                        Collectors.mapping(
                                positionLevel -> positionLevel.getQuestionLevel().getQuestionLevelId(),
                                Collectors.toList()
                        )
                ));

		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize());
		Page<ResponseGetQuestionsDto> questionList = questionVersionQueryRepository.findQuestions(
			groupedPositionLevels,
			requestDto,
			pageable
		);
		PagingUtil pagingUtil = new PagingUtil(questionList.getTotalElements(), questionList.getTotalPages(),
			questionList.getNumber(), questionList.getSize());

		return new ResponseListDto<ResponseGetQuestionsDto>(questionList.getContent(), pagingUtil);
	}
}

