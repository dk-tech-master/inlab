package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.ChecklistResultDto;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;

import java.util.List;

public interface ChecklistResultService {

    void createChecklistResult(InterviewQuestion interviewQuestion, InterviewResult interviewResult, List<ChecklistResultDto> checklistResultDtos);
}
