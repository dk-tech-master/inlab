package kr.inlab.www.service;

import kr.inlab.www.common.exception.*;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.RequestListDto;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import kr.inlab.www.dto.response.ResponseQuestionVersionsDto;
import kr.inlab.www.entity.*;
import kr.inlab.www.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;
	private final QuestionVersionRepository questionVersionRepository;
	private final PositionRepository positionRepository;
	private final QuestionTypeRepository questionTypeRepository;
	private final QuestionLevelRepository questionLevelRepository;
	private final ChecklistRepository checklistRepository;
	private final RelatedQuestionRepository relatedQuestionRepository;
	private final InterviewQuestionQueryRepository interviewQuestionQueryRepository;

	private void saveChecklists(List<String> checklists, QuestionVersion questionVersion) {
		List<Checklist> checklistEntities = checklists.stream()
			.map(checklist -> Checklist.builder()
				.content(checklist)
				.questionVersion(questionVersion)
				.build())
			.collect(Collectors.toList());

		checklistRepository.saveAll(checklistEntities);
	}

	@Transactional
	@Override
	public void createQuestion(RequestCreateQuestionDto requestDto) {
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
		QuestionVersion questionVersion = questionVersionRepository.save(QuestionVersion.builder()
			.title(requestDto.getTitle())
			.version(requestDto.getVersion())
			.question(question)
			.questionLevel(questionLevel)
			.build());

		saveChecklists(requestDto.getChecklists(), questionVersion);
	}

	@Override
	public ResponseGetQuestionDto getQuestion(Long questionId) {
		// Join되어 있는 데이터 조회
		QuestionVersion questionVersion = questionVersionRepository.findTopByQuestionQuestionIdAndIsLatest(questionId, YesNo.Y)
			.orElseThrow(QuestionVersionNotFoundException::new);
		// 체크리스트 조회
		List<Checklist> checklistEntities = checklistRepository.findAllByQuestionVersion(questionVersion);
		// 형변환
		List<String> checklists = checklistEntities.stream()
			.map(Checklist::getContent)
			.collect(Collectors.toList());

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
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), Sort.by(Sort.Direction.DESC, "questionVersionId"));
		Page<ResponseGetQuestionsDto> questionList = questionVersionRepository.findQuestions(
			requestDto.getPositionId(),
			requestDto.getQuestionTypeId(),
			requestDto.getQuestionLevelId(),
			requestDto.getTitleKeyword(),
			pageable
		);
		PagingUtil pagingUtil = new PagingUtil(questionList.getTotalElements(), questionList.getTotalPages(), questionList.getNumber(), questionList.getSize());

		return new ResponseListDto<ResponseGetQuestionsDto>(questionList.getContent(), pagingUtil);
	}

	@Override
	public ResponseListDto<ResponseQuestionVersionsDto> getQuestionVersions(RequestListDto requestDto, Long questionId) {
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), Sort.by(Sort.Direction.DESC, "version"));
		Page<ResponseQuestionVersionsDto> questionVersionList = questionVersionRepository.findQuestionVersions(questionId, pageable);
		PagingUtil pagingUtil = new PagingUtil(questionVersionList.getTotalElements(), questionVersionList.getTotalPages(), questionVersionList.getNumber(), questionVersionList.getSize());

		return new ResponseListDto<ResponseQuestionVersionsDto>(questionVersionList.getContent(), pagingUtil);
	}

	@Transactional
	@Override
	public void updateQuestion(RequestUpdateQuestionDto requestDto, Long questionId) {
		Question question = questionRepository.findById(questionId)
			.orElseThrow(QuestionNotFoundException::new);
		Position position = positionRepository.findById(requestDto.getPositionId())
			.orElseThrow(PositionNotFoundException::new);
		QuestionType questionType = questionTypeRepository.findById(requestDto.getQuestionTypeId())
			.orElseThrow(QuestionVersionNotFoundException::new);

		question.update(position, questionType);

		QuestionLevel questionLevel = questionLevelRepository.findById(requestDto.getQuestionLevelId())
			.orElseThrow(QuestionLevelNotFoundException::new);
		QuestionVersion latestQuestionVersion = questionVersionRepository.findTopByQuestionAndIsLatest(question, YesNo.Y)
			.orElseThrow(LatestQuestionVersionNotFoundException::new);

		QuestionVersion newQuestionVersion = QuestionVersion.builder()
			.title(requestDto.getTitle())
			.version(latestQuestionVersion.getVersion() + 1)
			.question(question)
			.questionLevel(questionLevel)
			.build();
		QuestionVersion savedQuestionVersion = questionVersionRepository.save(newQuestionVersion);

		saveChecklists(requestDto.getChecklists(), savedQuestionVersion);
		latestQuestionVersion.updateIsLatest(YesNo.N);
	}

	@Transactional
	@Override
	public void createRelatedQuestion(RequestCreateRelatedQuestionDto requestDto, Long questionId) {
		Question headQuestion = questionRepository.findById(questionId)
			.orElseThrow(QuestionNotFoundException::new);
		Question tailQuestion = questionRepository.findById(requestDto.getTailQuestionId())
			.orElseThrow(QuestionNotFoundException::new);

		relatedQuestionRepository.save(RelatedQuestion.builder()
			.headQuestion(headQuestion)
			.tailQuestion(tailQuestion)
			.build());
	}

	@Transactional
	@Override
	public void deleteRelatedQuestion(Long relatedQuestionId) {
		RelatedQuestion relatedQuestion = relatedQuestionRepository.findById(relatedQuestionId)
			.orElseThrow(QuestionNotFoundException::new);

		relatedQuestionRepository.delete(relatedQuestion);
	}
}