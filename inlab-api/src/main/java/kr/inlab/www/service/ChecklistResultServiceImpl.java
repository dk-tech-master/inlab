package kr.inlab.www.service;

import kr.inlab.www.common.exception.ChecklistNotFoundException;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.ChecklistResultDto;
import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.ChecklistResult;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;
import kr.inlab.www.repository.ChecklistRepository;
import kr.inlab.www.repository.ChecklistResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistResultServiceImpl implements ChecklistResultService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistResultRepository checklistResultRepository;

    @Override
    public void createChecklistResult(InterviewQuestion interviewQuestion, InterviewResult interviewResult, List<ChecklistResultDto> checklistResultDtos) {
        checklistResultDtos.stream().forEach(checklistResultDto -> {
            Checklist checklist = checklistRepository.findById(checklistResultDto.getChecklistId())
                    .orElseThrow(ChecklistNotFoundException::new);

            ChecklistResult checklistResult = ChecklistResult.builder()
                    .isChecked(checklistResultDto.getIsChecked())
                    .checklist(checklist)
                    .interviewQuestion(interviewQuestion)
                    .interviewResult(interviewResult)
                    .build();

            checklistResultRepository.save(checklistResult);
        });
    }
}
