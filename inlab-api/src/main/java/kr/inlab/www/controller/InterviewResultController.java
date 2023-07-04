package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.service.InterviewResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
