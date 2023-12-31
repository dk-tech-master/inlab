package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseGetQuestionsDto {

	private Long questionId;
	private String title;
	private Integer questionTypeId;
	private String questionTypeName;
	private Integer positionId;
	private String positionName;
	private Integer questionLevelId;
	private String questionLevelName;
	private Long questionVersionId;
	private Integer version;


	@Builder

	public ResponseGetQuestionsDto(Long questionId, String title, Integer questionTypeId, String questionTypeName, Integer positionId,
								   String positionName, Integer questionLevelId, String questionLevelName, Long questionVersionId, Integer version) {
		this.questionId = questionId;
		this.title = title;
		this.questionTypeId = questionTypeId;
		this.questionTypeName = questionTypeName;
		this.positionId = positionId;
		this.positionName = positionName;
		this.questionLevelId = questionLevelId;
		this.questionLevelName = questionLevelName;
		this.questionVersionId = questionVersionId;
		this.version = version;
	}
}
