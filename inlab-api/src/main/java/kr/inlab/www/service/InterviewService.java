package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionStartDto;

import java.util.List;

public interface InterviewService {

    void createInterview(RequestCreateInterviewDto requestDto);

    ResponseListDto<ResponseInterviewDto> getInterviews(Long userId ,RequestGetInterviewDto requestDto);

    void putInterview(Long interviewId, RequestCreateInterviewDto requestDto);

    List<ResponseInterviewQuestionStartDto> getInterviewQuestionStartList(Long interviewId);

    ResponseGetInterviewDto getInterview(Long interviewId);
}
