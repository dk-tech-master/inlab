package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestPositionDto;
import kr.inlab.www.dto.response.ResponsePositionListDto;

public interface PositionService {

    boolean createPosition(String name);

    ResponsePositionListDto getPosition(RequestPositionDto requestDto);
}
