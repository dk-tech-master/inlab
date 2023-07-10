package kr.inlab.www.dto.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateQuestionDto {

	private Integer positionId;
	private Integer questionTypeId;
	private Integer questionLevelId;
	private String title;
	private Integer version;
	private List<String> checklists;

	@Builder
	public RequestCreateQuestionDto(Integer positionId, Integer questionTypeId, Integer questionLevelId, String title,
		Integer version, List<String> checklists) {
		this.positionId = positionId;
		this.questionTypeId = questionTypeId;
		this.questionLevelId = questionLevelId;
		this.title = title;
		this.version = version;
		this.checklists = checklists;
	}
}
