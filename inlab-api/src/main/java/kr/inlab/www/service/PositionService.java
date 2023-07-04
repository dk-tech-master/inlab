package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponsePositionListDto;

public interface PositionService {

    void createPosition(RequestPositionNameDto requestDto);

    ResponsePositionListDto getPosition(RequestPositionDto requestDto);

    void deletePosition(Integer positionId);

    void updatePosition(Integer positionId, RequestPositionNameDto requestDto);

}
