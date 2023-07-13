package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestUpdateChecklistResultDto;
import kr.inlab.www.dto.response.ResponseChecklistResultDto;
import kr.inlab.www.service.ChecklistResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklist-result")
public class ChecklistResultController {

    private final ChecklistResultService checklistResultService;

    @PatchMapping
    public ResponseEntity<?> updateChecklistResult(@RequestBody RequestUpdateChecklistResultDto requestDto) {
        List<ResponseChecklistResultDto> responseChecklistResultDtoList = checklistResultService.updateChecklistResult(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseChecklistResultDtoList);
    }
}
