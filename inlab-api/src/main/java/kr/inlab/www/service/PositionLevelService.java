package kr.inlab.www.service;

import java.util.List;
import kr.inlab.www.dto.response.ResponseGetPositionLevelDto;
import kr.inlab.www.dto.request.RequestUpdatePositionLevelDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.entity.User;

public interface PositionLevelService {

    void createAdminPositionLevel(List<User> user, Position position);

    void updatePositionLevel(RequestUpdatePositionLevelDto requestDto);

    ResponseGetPositionLevelDto getPositionLevel(Long userId);
}
