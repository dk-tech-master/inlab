package kr.inlab.www.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class RequestUpdateQuestionDto {

	private Integer positionId;
	private Integer questionTypeId;
	private Integer questionLevelId;
	private String title;
	private List<String> checklists;
}


