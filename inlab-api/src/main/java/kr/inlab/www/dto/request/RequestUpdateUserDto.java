package kr.inlab.www.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdateUserDto {

    private String email;
    private String nickname;
    private String password;
}
