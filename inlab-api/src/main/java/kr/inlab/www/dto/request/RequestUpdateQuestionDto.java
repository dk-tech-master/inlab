package kr.inlab.www.dto.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUpdateQuestionDto {

	private Integer positionId;
	private Integer questionTypeId;
	private Integer questionLevelId;
	private String title;
	private List<String> checklists;

	@Builder
	public RequestUpdateQuestionDto(Integer positionId, Integer questionTypeId, Integer questionLevelId, String title,
		List<String> checklists) {
		this.positionId = positionId;
		this.questionTypeId = questionTypeId;
		this.questionLevelId = questionLevelId;
		this.title = title;
		this.checklists = checklists;
	}
}


