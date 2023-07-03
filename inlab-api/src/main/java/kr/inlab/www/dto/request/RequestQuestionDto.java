package kr.inlab.www.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestQuestionDto {

	// private Long positonId;
	// private Long questionTypeId;
	private String title;
	private int version;
}
