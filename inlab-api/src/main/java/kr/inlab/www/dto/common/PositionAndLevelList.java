package kr.inlab.www.dto.common;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PositionAndLevelList {

    private Integer positionId;

    private String positionName;

    List<LevelDto> levelListDto;

    @Getter
    public static class LevelDto {

        private Integer levelId;

        private String levelName;

        @Builder
        public LevelDto(Integer levelId, String levelName) {
            this.levelId = levelId;
            this.levelName = levelName;
        }
    }

    @Builder
    public PositionAndLevelList(Integer positionId, String positionName,
        List<LevelDto> levelListDto) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.levelListDto = levelListDto;
    }
}
