package kr.inlab.www.common.util;

import java.time.LocalDateTime;
import kr.inlab.www.dto.request.UserCreateRequestDto;
import kr.inlab.www.entity.User;

public class DtoConverter {

    public static User convert(UserCreateRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname());
        user.setPassword(dto.getPassword());
        user.setPasswordModifiedAt(LocalDateTime.now());
        user.setVerificationStatus(true);
        user.setLoginAttempt(0);
        user.setUserStatus("ACTIVE");
        return user;
    }
}

