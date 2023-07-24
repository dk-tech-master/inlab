package kr.inlab.www.dto.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PositionAndLevelInfo implements PositionLevelInfoGetter {

    private Integer positionId;

    private String positionName;

    private Integer levelId;

    private String levelName;

    @Override
    public Integer getPositionId() {
        return this.positionId;
    }

    @Override
    public Integer getLevelId() {
        return this.levelId;
    }

    @Builder
    public PositionAndLevelInfo(Integer positionId, String positionName, Integer levelId, String levelName) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.levelId = levelId;
        this.levelName = levelName;
    }
}
