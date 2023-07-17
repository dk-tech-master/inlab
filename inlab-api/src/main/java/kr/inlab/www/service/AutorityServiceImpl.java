package kr.inlab.www.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import kr.inlab.www.common.exception.QuestionNotFoundException;
import kr.inlab.www.common.exception.QuestionVersionNotFoundException;
import kr.inlab.www.common.exception.UserHasNotAuthorityToQuestion;
import kr.inlab.www.common.exception.UserNotFoundException;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.entity.PositionLevel;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.PositionLevelRepository;
import kr.inlab.www.repository.QuestionRepository;
import kr.inlab.www.repository.QuestionVersionRepository;
import kr.inlab.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorityServiceImpl implements AutorityService {

	private final UserService userService;
	private final QuestionRepository questionRepository;
	private final QuestionVersionRepository questionVersionRepository;
	private final UserRepository userRepository;
	private final PositionLevelRepository positionLevelRepository;


	public void checkUserHasAuthorityToQuestion(Long questionId) {
		if (userService.isAdmin()) return;
		List<PositionLevel> positionLevels = getPositionLevels();

		Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
		Integer positionId = question.getPosition().getPositionId();
		Integer questionLevelId = questionVersionRepository.findTopByQuestionQuestionIdAndIsLatest(questionId, YesNo.Y)
			.orElseThrow(QuestionVersionNotFoundException::new).getQuestionLevel().getQuestionLevelId();

		if (positionLevels.stream().noneMatch(positionLevel ->
			positionId.equals(positionLevel.getPosition().getPositionId()) &&
				questionLevelId.equals(positionLevel.getQuestionLevel().getQuestionLevelId())
		)) {
			throw new UserHasNotAuthorityToQuestion();
		}
	};

	public void checkUserHasAuthorityToQuestion(Integer positionId, Integer questionLevelId) {
		if (userService.isAdmin()) return;
		List<PositionLevel> positionLevels = getPositionLevels();

		if (positionLevels.stream().noneMatch(positionLevel ->
			positionId.equals(positionLevel.getPosition().getPositionId()) &&
				questionLevelId.equals(positionLevel.getQuestionLevel().getQuestionLevelId())
		)) {
			throw new UserHasNotAuthorityToQuestion();
		}
	};

	public List<PositionLevel> getPositionLevels() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		List<PositionLevel> positionLevels = positionLevelRepository.findByUser(user);
		return positionLevels;
	}
}
