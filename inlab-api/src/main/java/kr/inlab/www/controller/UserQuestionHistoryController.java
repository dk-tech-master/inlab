package kr.inlab.www.controller;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestUserQuestionHistoryListDto;
import kr.inlab.www.service.UserQuestionHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-question-history")
public class UserQuestionHistoryController {

    private final UserQuestionHistoryService userQuestionHistoryService;

    @GetMapping
    public ResponseEntity<?> getUserQuestionHistoryList(@ModelAttribute RequestUserQuestionHistoryListDto requestDto) {
        ResponseListDto responseDto = userQuestionHistoryService.getUserQuestionHistoryList(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
