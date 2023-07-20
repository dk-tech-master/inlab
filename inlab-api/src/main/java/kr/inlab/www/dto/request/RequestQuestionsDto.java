package kr.inlab.www.dto.request;


import kr.inlab.www.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestQuestionsDto extends RequestListDto {

	private Integer positionId;
	private Integer questionTypeId;
	private Integer questionLevelId;
	private String titleKeyword;

	@Builder
	public RequestQuestionsDto(Integer positionId, Integer questionTypeId, Integer questionLevelId,
		String titleKeyword) {
		this.positionId = positionId;
		this.questionTypeId = questionTypeId;
		this.questionLevelId = questionLevelId;
		this.titleKeyword = titleKeyword;
	}
}
