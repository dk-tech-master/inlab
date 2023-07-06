package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.dto.response.ResponseInterviewResultDto;

public interface InterviewResultService {

    void createInterviewResult(RequestCreateInterviewResultDto requestDto);

    ResponseInterviewResultDto getInterviewResult(Long interviewResultId);
}
