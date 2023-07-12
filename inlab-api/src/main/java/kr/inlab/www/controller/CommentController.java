package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestUpdateCommentDto;
import kr.inlab.www.dto.response.ResponseCommentDto;
import kr.inlab.www.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody RequestUpdateCommentDto requestDto, @PathVariable Long commentId) {
        ResponseCommentDto responseUpdatedCommentDto = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseUpdatedCommentDto);
    }
}
