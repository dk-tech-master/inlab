package kr.inlab.www.service;

import java.util.List;
import javax.management.relation.RoleNotFoundException;
import kr.inlab.www.dto.common.PositionAndLevelList;
import kr.inlab.www.dto.common.PositionDto;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestGetPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponseGetAllPositionLevelDto;
import kr.inlab.www.dto.response.ResponsePositionDto;

public interface PositionService {

    void createPosition(RequestPositionNameDto requestDto) throws RoleNotFoundException;

    ResponseListDto<ResponsePositionDto> getPosition(RequestGetPositionDto requestDto);

    void deletePosition(Integer positionId);

    void updatePosition(Integer positionId, RequestPositionNameDto requestDto);

    List<PositionAndLevelList> getPositionOnCategory();

    List<PositionDto> getAllPosition();
}
