package kr.inlab.www.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.QuestionVersion;
import kr.inlab.www.repository.QuestionRepository;
import kr.inlab.www.repository.QuestionVersionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;
	private final QuestionVersionRepository questionVersionRepository;
	// private final PositionRepository positionRepository;
	// private final QuestionTypeRepository questionTypeRepository;

	@Transactional
	@Override
	public void createQuestion(RequestCreateQuestionDto requestDto) {
		// positionId, questionTypeId 받아오기
		// Position position = positionRepository.findById(requestDto.getPositionId())
		// 	.orElseThrow(() -> new RuntimeException("Position not found"));
		// QuestionType questionType = questionTypeRepository.findById(requestDto.getQuestionTypeId())
		// 	.orElseThrow(() -> new RuntimeException("Question type not found"));

		// question 저장
		Question question = questionRepository.save(Question.builder()
			// .position(position)
			// .questionType(questionType)
			.build());

		// question_version 저장
		questionVersionRepository.save(QuestionVersion.builder()
			.title(requestDto.getTitle())
			.version(requestDto.getVersion())
			.question(question)
			.build());
	}

}
