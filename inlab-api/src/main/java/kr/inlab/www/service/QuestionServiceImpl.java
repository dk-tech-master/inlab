package kr.inlab.www.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.request.RequestQuestionDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.QuestionType;
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
	public void createQuestion(RequestQuestionDto requestDto) {
		// positionId, questionTypeId 받아오기
		// Position position = positionRepository.findById(requestDto.getPositionId())
		// 	.orElseThrow(() -> new RuntimeException("Position not found")); // customize this exception as needed
		// QuestionType questionType = questionTypeRepository.findById(requestDto.getQuestionTypeId())
		// 	.orElseThrow(() -> new RuntimeException("Question type not found"));

		// question 저장
		Question question = questionRepository.save(Question.builder()
			// .position(position)
			// .questionType(questionType)
			.build());

		// question_version 저장
		questionVersionRepository.save(QuestionVersion.builder()
			.createdAt(LocalDateTime.now())
			.isLatest(YesNo.Y)
			.title(requestDto.getTitle())
			.version(requestDto.getVersion())
			.question(question)
			.build());
	}

}
