package kr.inlab.www.dto.request;

import java.util.List;
import kr.inlab.www.dto.common.PositionAndLevelInfo;
import lombok.Getter;

@Getter
public class RequestUpdatePositionLevelDto {

    private Long userId;

    private List<PositionAndLevelInfo> positionAndLevelInfos;
}
