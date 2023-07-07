package kr.inlab.www.dto.response;


import java.util.List;
import kr.inlab.www.dto.common.PositionAndLevelInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetPositionLevelDto {

    private List<PositionAndLevelInfo> positionIdAndLevelIds;

    @Builder
    public ResponseGetPositionLevelDto(List<PositionAndLevelInfo> positionIdAndLevelIds) {
        this.positionIdAndLevelIds = positionIdAndLevelIds;
    }
}
