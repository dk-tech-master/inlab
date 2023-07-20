package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateQuestionDto {

    private Integer positionId;
    private Integer questionTypeId;
    private Integer questionLevelId;
    private String title;
    private List<String> checklists;

    @Builder
    public RequestCreateQuestionDto(Integer positionId, Integer questionTypeId, Integer questionLevelId, String title,
                                    List<String> checklists) {
        this.positionId = positionId;
        this.questionTypeId = questionTypeId;
        this.questionLevelId = questionLevelId;
        this.title = title;
        this.checklists = checklists;
    }
}
