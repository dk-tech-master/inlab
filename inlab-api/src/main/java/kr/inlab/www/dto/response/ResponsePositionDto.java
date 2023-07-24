package kr.inlab.www.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponsePositionDto {

    private Integer positionId;

    private String positionName;

    private Long questionCount;

    public ResponsePositionDto(Integer positionId, String positionName, Long questionCount) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.questionCount = questionCount;
    }
}
