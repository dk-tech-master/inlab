package kr.inlab.www.dto.response;

import lombok.Getter;

@Getter
public class ResponseQuestionTypeDto {

    private Integer questionTypeId;

    private String questionTypeName;

    private Long questionCount;

    public ResponseQuestionTypeDto(Integer questionTypeId, String questionTypeName, Long questionCount) {
        this.questionTypeId = questionTypeId;
        this.questionTypeName = questionTypeName;
        this.questionCount = questionCount;
    }
}
