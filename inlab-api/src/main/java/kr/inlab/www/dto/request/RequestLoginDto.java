package kr.inlab.www.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLoginDto {

    // todo validation
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String username;
    private String password;
}
