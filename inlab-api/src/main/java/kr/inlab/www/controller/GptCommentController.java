package kr.inlab.www.controller;

import kr.inlab.www.dto.response.ResponseGptCommentDto;
import kr.inlab.www.service.GptCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt-comment")
public class GptCommentController {

    private final GptCommentService gptCommentService;

    @GetMapping("/{gptCommentId}")
    public ResponseEntity<?> getGptComment(@PathVariable Long gptCommentId) {
        log.info("gptCommentId: {}", gptCommentId);
        ResponseGptCommentDto responseGptCommentDto = gptCommentService.getGptComment(gptCommentId);

        return ResponseEntity.status(HttpStatus.OK).body(responseGptCommentDto);
    }
}
