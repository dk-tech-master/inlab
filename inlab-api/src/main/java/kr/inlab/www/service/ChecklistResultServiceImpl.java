package kr.inlab.www.service;

import kr.inlab.www.common.exception.ChecklistNotFoundException;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.ChecklistResultDto;
import kr.inlab.www.dto.response.ResponseChecklistDto;
import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.ChecklistResult;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.repository.ChecklistRepository;
import kr.inlab.www.repository.ChecklistResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistResultServiceImpl implements ChecklistResultService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistResultRepository checklistResultRepository;

    @Transactional
    @Override
    public void createChecklistResult(InterviewQuestionResult interviewQuestionResult, List<ChecklistResultDto> checklistResultDtoList) {
        checklistResultDtoList.stream().forEach(checklistResultDto -> {
            Checklist checklist = checklistRepository.findById(checklistResultDto.getChecklistId())
                    .orElseThrow(ChecklistNotFoundException::new);

            ChecklistResult checklistResult = ChecklistResult.builder()
                    .isChecked(checklistResultDto.getIsChecked())
                    .checklist(checklist)
                    .interviewQuestionResult(interviewQuestionResult)
                    .build();

            checklistResultRepository.save(checklistResult);
        });
    }

    @Override
    public List<ResponseChecklistDto> getChecklistResultList(InterviewQuestionResult interviewQuestionResult) {
        List<ChecklistResult> checklistResultList = checklistResultRepository.findAllByInterviewQuestionResult(interviewQuestionResult);
        List<ResponseChecklistDto> responseChecklistDtoList = checklistResultList.stream().map(checklistResult -> ResponseChecklistDto.builder()
                .content(checklistResult.getChecklist().getContent())
                .isChecked(checklistResult.getIsChecked())
                .build()).collect(Collectors.toList());

        return responseChecklistDtoList;
    }
}
