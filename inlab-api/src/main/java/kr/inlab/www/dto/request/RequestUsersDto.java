package kr.inlab.www.dto.request;

import kr.inlab.www.dto.common.RequestListDto;
import lombok.Getter;

@Getter
public class RequestUsersDto extends RequestListDto {

    private String nickname;
    private Boolean isVerified;
}
