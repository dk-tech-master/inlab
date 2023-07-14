package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED )
public class RequestCreateQuestionTypeDto {

    private Integer positionId;
    private String questionTypeName;

    @Builder
    public RequestCreateQuestionTypeDto(Integer positionId, String questionTypeName) {
        this.positionId = positionId;
        this.questionTypeName = questionTypeName;
    }
}
