package kr.inlab.www.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestCreateRelatedQuestionDto {

	private Long headQuestionId;
	private Long tailQuestionId;
}
