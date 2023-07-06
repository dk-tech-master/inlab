package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.dto.response.ResponseInterviewResultDto;
import kr.inlab.www.service.InterviewResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview-result")
public class InterviewResultController {

    private final InterviewResultService interviewResultService;

    @PostMapping
    public ResponseEntity<?> createInterviewResult(@RequestBody RequestCreateInterviewResultDto requestDto) {
        interviewResultService.createInterviewResult(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{interviewResultId}")
    public ResponseEntity<?> getInterviewResult(@PathVariable Long interviewResultId) {
        ResponseInterviewResultDto responseInterviewResultDto = interviewResultService.getInterviewResult(interviewResultId);
        return ResponseEntity.status(HttpStatus.OK).body(responseInterviewResultDto);
    }
}
