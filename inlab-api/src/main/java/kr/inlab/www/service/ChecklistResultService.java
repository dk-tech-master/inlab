package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.ChecklistResultDto;
import kr.inlab.www.dto.response.ResponseChecklistDto;
import kr.inlab.www.entity.InterviewQuestionResult;

import java.util.List;

public interface ChecklistResultService {

    void createChecklistResult(InterviewQuestionResult interviewQuestionResult, List<ChecklistResultDto> checklistResultDtoList);

    List<ResponseChecklistDto> getChecklistResultList(InterviewQuestionResult interviewQuestionResult);
}
