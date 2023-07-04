package kr.inlab.www.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inlab.www.dto.request.RequestCreateQuestionDto;
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

	@Transactional
	@Override
	public void createQuestion(RequestCreateQuestionDto requestDto) {
		// 유효성은 한번 pull 한 다음 작업 진행 예정

		// positionId, questionTypeId 받아오기
		Position position = positionRepository.findById(requestDto.getPositionId())
			.orElseThrow(() -> new RuntimeException("Position not found"));
		QuestionType questionType = questionTypeRepository.findById(requestDto.getQuestionTypeId())
			.orElseThrow(() -> new RuntimeException("Question type not found"));

		// question 저장
		Question question = questionRepository.save(Question.builder()
			.position(position)
			.questionType(questionType)
			.build());

		// questionLevelId 받아오기
		QuestionLevel questionLevel = questionLevelRepository.findById(requestDto.getQuestionLevelId())
			.orElseThrow(() -> new RuntimeException("QuestionLevel not found"));

		// question_version 저장
		QuestionVersion questionVersion = questionVersionRepository.save(QuestionVersion.builder()
			.title(requestDto.getTitle())
			.version(requestDto.getVersion())
			.question(question)
			.questionLevel(questionLevel)
			.build());

		// question_checklists 저장
		List<String> checklists = requestDto.getChecklists();
		List<Checklist> checklistEntities = new ArrayList<>();

		for (String checklist : checklists) {
			Checklist checklistEntity = Checklist.builder()
				.content(checklist)
				.questionVersion(questionVersion)
				.build();
			checklistEntities.add(checklistEntity);
		}

		checklistRepository.saveAll(checklistEntities);
	}
}