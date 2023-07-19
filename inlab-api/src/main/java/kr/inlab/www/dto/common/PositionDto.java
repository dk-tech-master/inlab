package kr.inlab.www.dto.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionDto {

    private Integer positionId;

    private String positionName;

    @Builder
    public PositionDto(Integer positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }
}
