package kr.inlab.www.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class RequestCreateQuestionDto {

	private Integer positionId;
	private Integer questionTypeId;
	private Integer questionLevelId;
	private String title;
	private Integer version;
	private List<String> checklists;
}
