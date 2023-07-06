package kr.inlab.www.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inlab.www.common.exception.*;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.Position;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.QuestionLevel;
import kr.inlab.www.entity.QuestionType;
import kr.inlab.www.entity.QuestionVersion;
import kr.inlab.www.repository.ChecklistRepository;
import kr.inlab.www.repository.PositionRepository;
import kr.inlab.www.repository.QuestionLevelRepository;
import kr.inlab.www.repository.QuestionRepository;
import kr.inlab.www.repository.QuestionTypeRepository;
import kr.inlab.www.repository.QuestionVersionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;
	private final QuestionVersionRepository questionVersionRepository;
	private final PositionRepository positionRepository;
	private final QuestionTypeRepository questionTypeRepository;
	private final QuestionLevelRepository questionLevelRepository;
	private final ChecklistRepository checklistRepository;

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
		// positionId, questionTypeId 받아오기
		Position position = positionRepository.findById(requestDto.getPositionId())
			.orElseThrow(PositionNotFoundException::new);
		QuestionType questionType = questionTypeRepository.findById(requestDto.getQuestionTypeId())
			.orElseThrow(QuestionTypeNotFoundException::new);

		// question 저장
		Question question = questionRepository.save(Question.builder()
			.position(position)
			.questionType(questionType)
			.build());

		// questionLevelId 받아오기
		QuestionLevel questionLevel = questionLevelRepository.findById(requestDto.getQuestionLevelId())
			.orElseThrow(QuestionLevelNotFoundException::new);

		// question_version 저장
		QuestionVersion questionVersion = questionVersionRepository.save(QuestionVersion.builder()
			.title(requestDto.getTitle())
			.version(requestDto.getVersion())
			.question(question)
			.questionLevel(questionLevel)
			.build());

		// question_checklists 저장
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
		Sort sort  = Sort.by(Sort.Direction.DESC, "questionVersionId");
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), sort);
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

	@Transactional
	@Override
	public void updateQuestion(RequestUpdateQuestionDto requestDto, Long questionId) {

		// questionId를 사용하여 기존의 질문을 가져옵니다.
		Question question = questionRepository.findById(questionId)
			.orElseThrow(QuestionNotFoundException::new);

		// requestDto에서 position과 question type을 가져옵니다.
		Position position = positionRepository.findById(requestDto.getPositionId())
			.orElseThrow(PositionNotFoundException::new);
		QuestionType questionType = questionTypeRepository.findById(requestDto.getQuestionTypeId())
			.orElseThrow(QuestionVersionNotFoundException::new);

		// 질문을 업데이트합니다.
		question.updatePosition(position);
		question.updateQuestionType(questionType);

		// requestDto에서 questionLevel을 가져옵니다.
		QuestionLevel questionLevel = questionLevelRepository.findById(requestDto.getQuestionLevelId())
			.orElseThrow(QuestionLevelNotFoundException::new);

		// 가장 최신의 questionVersion을 가져옵니다.
		QuestionVersion latestQuestionVersion = questionVersionRepository.findTopByQuestionAndIsLatest(question, YesNo.Y)
			.orElseThrow(LatestQuestionVersionNotFoundException::new);

		// 질문의 새 버전을 생성합니다.
		QuestionVersion newQuestionVersion = QuestionVersion.builder()
			.title(requestDto.getTitle())
			.version(latestQuestionVersion.getVersion() + 1) // 새 버전은 마지막 버전 + 1
			.question(question)
			.questionLevel(questionLevel)
			.build();

		// 새 버전을 저장합니다.
		QuestionVersion savedQuestionVersion = questionVersionRepository.save(newQuestionVersion);

		// 새 버전에 대한 새로운 체크리스트를 생성합니다.
		saveChecklists(requestDto.getChecklists(), savedQuestionVersion);

		// 마지막으로, 이전 버전을 최신이 아님으로 표시합니다.
		latestQuestionVersion.updateIsLatest(YesNo.N);
	}
}