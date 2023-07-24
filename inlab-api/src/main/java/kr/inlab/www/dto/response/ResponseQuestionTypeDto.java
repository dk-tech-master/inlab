package kr.inlab.www.dto.response;

import lombok.Getter;

@Getter
public class ResponseQuestionTypeDto {

    private Integer positionId;

    private String positionName;

    private Integer questionTypeId;

    private String questionTypeName;

    private Long questionCount;



    public ResponseQuestionTypeDto(Integer positionId, String positionName, Integer questionTypeId, String questionTypeName,
        Long questionCount) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.questionTypeId = questionTypeId;
        this.questionTypeName = questionTypeName;
        this.questionCount = questionCount;
    }
}
