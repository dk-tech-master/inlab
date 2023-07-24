package kr.inlab.www.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestCreateRelatedQuestionDto {

	private Long headQuestionId;
	private Long tailQuestionId;

	@Builder
	public RequestCreateRelatedQuestionDto(Long headQuestionId, Long tailQuestionId) {
		this.headQuestionId = headQuestionId;
		this.tailQuestionId = tailQuestionId;
	}
}
