package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestUpdateInterviewQuestionResultDto;
import kr.inlab.www.service.InterviewQuestionResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview-question-result")
public class InterviewQuestionResultController {

    private final InterviewQuestionResultService interviewQuestionResultService;

    @PatchMapping
    public ResponseEntity<?> updateInterviewQuestionResult(@RequestBody RequestUpdateInterviewQuestionResultDto requestDto) {
        interviewQuestionResultService.updateInterviewQuestionResult(requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
