package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseQuestionVersionsDto {

	private String title;
	private Integer positionId;
	private String positionName;
	private Integer questionTypeId;
	private String questionTypeName;
	private Integer questionLevelId;
	private String questionLevelName;
	private Integer version;

	@Builder
	public ResponseQuestionVersionsDto(String title, Integer positionId, String positionName, Integer questionTypeId,
		String questionTypeName, Integer questionLevelId, String questionLevelName, Integer version) {
		this.title = title;
		this.positionId = positionId;
		this.positionName = positionName;
		this.questionTypeId = questionTypeId;
		this.questionTypeName = questionTypeName;
		this.questionLevelId = questionLevelId;
		this.questionLevelName = questionLevelName;
		this.version = version;
	}
}
