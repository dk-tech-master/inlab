package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUpdateQuestionTypeDto {

    private Integer positionId;
    private String questionTypeName;

    @Builder
    public RequestUpdateQuestionTypeDto(Integer positionId, String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }
}