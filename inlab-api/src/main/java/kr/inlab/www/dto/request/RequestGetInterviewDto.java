package kr.inlab.www.dto.request;

import kr.inlab.www.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class RequestGetInterviewDto extends RequestListDto {

    private String interviewTitle;

    private String nickname;
}
