package kr.inlab.www.service;

import kr.inlab.www.dto.response.ResponseGetPositionLevelDto;
import kr.inlab.www.dto.request.RequestUpdatePositionLevelDto;

public interface PositionLevelService {

    void updatePositionLevel(RequestUpdatePositionLevelDto requestDto);

    ResponseGetPositionLevelDto getPositionLevel(Long userId);
}
