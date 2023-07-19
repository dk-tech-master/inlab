package kr.inlab.www.dto.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionLevelDto {

    private Integer questionLevelId;

    private String questionLevelName;

    @Builder
    public QuestionLevelDto(Integer questionLevelId, String questionLevelName) {
        this.questionLevelId = questionLevelId;
        this.questionLevelName = questionLevelName;
    }
}
