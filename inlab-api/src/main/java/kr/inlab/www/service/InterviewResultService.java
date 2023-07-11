package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.dto.request.RequestInterviewResultListDto;
import kr.inlab.www.dto.response.ResponseInterviewResultDto;
import kr.inlab.www.dto.response.ResponseInterviewResultListDto;

public interface InterviewResultService {

    void createInterviewResult(RequestCreateInterviewResultDto requestDto);

    ResponseInterviewResultDto getInterviewResult(Long interviewResultId);

    ResponseListDto getInterviewResultList(RequestInterviewResultListDto requestInterviewResultListDto);
}
