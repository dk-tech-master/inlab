package kr.inlab.www.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateRequestDto {

    // todo validation
    private String email;
    private String nickname;
    private String password;
}
