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
public class RequestRelatedQuestionsDto extends RequestListDto {

	private Long questionId;

	@Builder
	public RequestRelatedQuestionsDto(Long questionId) {
		this.questionId = questionId;
	}
}
