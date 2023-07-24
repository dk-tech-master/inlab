package kr.inlab.www.dto.response;

import kr.inlab.www.dto.common.PositionDto;
import kr.inlab.www.dto.common.QuestionLevelDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseGetAllPositionLevelDto {

    List<PositionDto> positionList;
    List<QuestionLevelDto> questionLevelList;

    @Builder
    public ResponseGetAllPositionLevelDto(List<PositionDto> positionList, List<QuestionLevelDto> questionLevelList) {
        this.positionList = positionList;
        this.questionLevelList = questionLevelList;
    }
}
