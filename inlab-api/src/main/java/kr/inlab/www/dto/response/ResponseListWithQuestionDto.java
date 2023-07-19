package kr.inlab.www.dto.response;

import java.util.List;

import kr.inlab.www.common.util.PagingUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseListWithQuestionDto<T> {
	private String questionTitle;
	List<T> responseList;
	private PagingUtil pagingUtil;

	@Builder
	public ResponseListWithQuestionDto(String questionTitle, List<T> responseList, PagingUtil pagingUtil) {
		this.questionTitle = questionTitle;
		this.responseList = responseList;
		this.pagingUtil = pagingUtil;
	}
}
