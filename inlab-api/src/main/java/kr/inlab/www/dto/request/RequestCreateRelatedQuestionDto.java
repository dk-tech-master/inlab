package kr.inlab.www.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestCreateRelatedQuestionDto {

	private List<Long> tailQuestionIds;

	@Builder
	public RequestCreateRelatedQuestionDto(List<Long> tailQuestionId) {
		this.tailQuestionIds = tailQuestionIds;
	}
}
