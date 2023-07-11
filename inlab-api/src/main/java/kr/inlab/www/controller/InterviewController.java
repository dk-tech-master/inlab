package kr.inlab.www.controller;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;
import kr.inlab.www.service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ResourceBundle;

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
    public ResponseEntity getInterview(@ModelAttribute RequestGetInterviewDto requestDto, @PathVariable Long userId){
        ResponseListDto<ResponseInterviewDto> responseDto = interviewService.getInterview(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{InterviewId}")
    public ResponseEntity putInterview(@RequestBody RequestCreateInterviewDto requestDto,@PathVariable Long InterviewId) {
        interviewService.putInterview(InterviewId,requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
