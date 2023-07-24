package kr.inlab.www.service;

import java.util.List;

import kr.inlab.www.entity.PositionLevel;

public interface AuthorityService {

	void checkUserHasAuthorityToQuestion(Long questionId);

	void checkUserHasAuthorityToQuestion(Integer positionId, Integer questionLevelId);

	List<PositionLevel> getPositionLevels();
}
