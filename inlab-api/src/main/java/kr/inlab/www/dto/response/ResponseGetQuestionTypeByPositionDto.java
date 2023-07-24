package kr.inlab.www.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseGetQuestionTypeByPositionDto {

    private Integer questionTypeId;

    private String questionTypeName;

    @Builder
    public ResponseGetQuestionTypeByPositionDto(Integer questionTypeId, String questionTypeName) {
        this.questionTypeId = questionTypeId;
        this.questionTypeName = questionTypeName;
    }
}
