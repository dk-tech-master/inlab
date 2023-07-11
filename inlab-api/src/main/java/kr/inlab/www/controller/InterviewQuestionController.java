package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestCreateInterviewQuestionDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionDto;
import kr.inlab.www.service.InterviewQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview/interview-question")
public class InterviewQuestionController {

    private final InterviewQuestionService interviewQuestionService;
    @PostMapping
    public ResponseEntity createInterviewQuestion(@RequestBody RequestCreateInterviewQuestionDto requestDto) {
        interviewQuestionService.createInterviewQuestion(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{interviewId}")
    public ResponseEntity getInterviewQuestion(@PathVariable Long interviewId) {
        List<ResponseInterviewQuestionDto> interviewQuestionList = interviewQuestionService.getInterviewQuestionList(interviewId);
        return ResponseEntity.status(HttpStatus.OK).body(interviewQuestionList);
    }

    @DeleteMapping("/{interviewQuestionId}")
    public ResponseEntity deleteInterviewQuestion(@PathVariable Long interviewQuestionId) {
        interviewQuestionService.deleteInterviewQuestion(interviewQuestionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
