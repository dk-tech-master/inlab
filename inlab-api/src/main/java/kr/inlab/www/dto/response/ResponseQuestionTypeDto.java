package kr.inlab.www.dto.response;

import lombok.Getter;

@Getter
public class ResponseQuestionTypeDto {

    private String positionName;

    private Integer questionTypeId;

    private String questionTypeName;

    private Long questionCount;

    public ResponseQuestionTypeDto(String positionName, Integer questionTypeId, String questionTypeName,
        Long questionCount) {
        this.positionName = positionName;
        this.questionTypeId = questionTypeId;
        this.questionTypeName = questionTypeName;
        this.questionCount = questionCount;
    }
}
