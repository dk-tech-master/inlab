package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestGetPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponsePositionDto;

public interface PositionService {

    void createPosition(RequestPositionNameDto requestDto);

    ResponseListDto<ResponsePositionDto> getPosition(RequestGetPositionDto requestDto);

    void deletePosition(Integer positionId);

    void updatePosition(Integer positionId, RequestPositionNameDto requestDto);

}
