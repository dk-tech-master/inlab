package kr.inlab.www.controller;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionStartDto;
import kr.inlab.www.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity createInterview(@RequestBody RequestCreateInterviewDto requestDto) {
        interviewService.createInterview(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("@userServiceImpl.isAdminOrSelf(#userId)")
    @GetMapping("/{userId}")
    public ResponseEntity getInterviews(@ModelAttribute RequestGetInterviewDto requestDto, @PathVariable Long userId) {
        ResponseListDto<ResponseInterviewDto> responseDto = interviewService.getInterviews(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{interviewId}/info")
    public ResponseEntity getInterview(@PathVariable Long interviewId) {
        return ResponseEntity.ok(interviewService.getInterview(interviewId));
    }

    @PutMapping("/{InterviewId}")
    public ResponseEntity putInterview(@RequestBody RequestCreateInterviewDto requestDto, @PathVariable Long InterviewId) {
        interviewService.putInterview(InterviewId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/start/{interviewId}")
    public ResponseEntity getInterviewQuestionsAndCheckList(@PathVariable Long interviewId) {
        List<ResponseInterviewQuestionStartDto> interviewStartList = interviewService.getInterviewQuestionStartList(interviewId);
        return ResponseEntity.ok().body(interviewStartList);
    }
}
