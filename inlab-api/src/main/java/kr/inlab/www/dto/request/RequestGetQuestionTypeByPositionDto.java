package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestGetQuestionTypeByPositionDto {

    private Integer positionId;

    public RequestGetQuestionTypeByPositionDto(Integer positionId) {
        this.positionId = positionId;
    }
}
