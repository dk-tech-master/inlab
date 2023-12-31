package kr.inlab.www.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseGetQuestionDto {

	private String title;
	private Integer questionTypeId;
	private String questionTypeName;
	private Integer positionId;
	private String positionName;
	private Integer questionLevelId;
	private String questionLevelName;
	private Integer version;
	private List<String> checklists;

	@Builder
	public ResponseGetQuestionDto(String title, Integer questionTypeId, String questionTypeName, Integer positionId,
		String positionName, Integer questionLevelId, String questionLevelName, Integer version,
		List<String> checklists) {
		this.title = title;
		this.questionTypeId = questionTypeId;
		this.questionTypeName = questionTypeName;
		this.positionId = positionId;
		this.positionName = positionName;
		this.questionLevelId = questionLevelId;
		this.questionLevelName = questionLevelName;
		this.version = version;
		this.checklists = checklists;
	}
}
