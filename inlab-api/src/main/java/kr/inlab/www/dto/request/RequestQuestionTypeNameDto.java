package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED )
public class RequestQuestionTypeNameDto {

    private String questionTypeName;

    @Builder
    public RequestQuestionTypeNameDto(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }
}
