package kr.inlab.www.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RequestCreateUserDto {

    @NotBlank(message = "이메일 정보를 입력해주세요")
    @Email(message = "이메일 형식을 확인해주세요")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min = 2, max = 20, message = "닉네임의 길이는 2~20 자 입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, message = "비밀번호의 길이는 4자 이상이어야 합니다.")
    private String password;
}
