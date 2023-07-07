package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;

public interface InterviewService {

    void createInterview(RequestCreateInterviewDto requestDto);

    ResponseListDto<ResponseInterviewDto> getInterview(Long userId ,RequestGetInterviewDto requestDto);

}