package kr.inlab.www.dto.request;

import kr.inlab.www.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestRelatedQuestionsDto extends RequestListDto {
}
