package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseGetRelatedQuestionsDto {

	private Long relatedQuestionId;
	private Long questionId;
	private String title;
	private Integer questionTypeId;
	private String questionTypeName;
	private Integer positionId;
	private String positionName;
	private Integer questionLevelId;
	private String questionLevelName;
	private Integer version;

	@Builder
	public ResponseGetRelatedQuestionsDto(Long relatedQuestionId, Long questionId, String title, Integer questionTypeId, String questionTypeName, Integer positionId,
		String positionName, Integer questionLevelId, String questionLevelName, Integer version) {
		this.relatedQuestionId = relatedQuestionId;
		this.questionId = questionId;
		this.title = title;
		this.questionTypeId = questionTypeId;
		this.questionTypeName = questionTypeName;
		this.positionId = positionId;
		this.positionName = positionName;
		this.questionLevelId = questionLevelId;
		this.questionLevelName = questionLevelName;
		this.version = version;
	}
}
