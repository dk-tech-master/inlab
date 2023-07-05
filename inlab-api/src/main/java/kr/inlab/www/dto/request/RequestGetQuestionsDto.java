package kr.inlab.www.dto.request;

import net.bytebuddy.implementation.bind.annotation.Super;

import kr.inlab.www.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestGetQuestionsDto extends RequestListDto {

}
