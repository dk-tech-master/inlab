package kr.inlab.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionVersionsDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import kr.inlab.www.dto.response.ResponseQuestionVersionsDto;
import kr.inlab.www.service.QuestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    // 질문 등록 (#15)
    @PostMapping
    public ResponseEntity<Void> createQuestion(@RequestBody RequestCreateQuestionDto requestDto) {
        questionService.createQuestion(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 질문 검색 및 전체 조회 (#12)
    @GetMapping
    public ResponseEntity<ResponseListDto<ResponseGetQuestionsDto>> getQuestions(@ModelAttribute RequestQuestionsDto requestDto) {
        ResponseListDto<ResponseGetQuestionsDto> responseDto = questionService.getQuestions(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 질문 상세 조회 (#14)
    @GetMapping("/{questionId}")
    public ResponseEntity<ResponseGetQuestionDto> getQuestion(@PathVariable Long questionId, @AuthenticationPrincipal String username) {
        ResponseGetQuestionDto responseDto = questionService.getQuestion(questionId, username);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 해당 질문에 대한 질문 버전 전체 조회 (#13)
    @GetMapping("/{questionId}/question-versions")
    public ResponseEntity<ResponseListDto<ResponseQuestionVersionsDto>> getQuestionVersions(@ModelAttribute RequestQuestionVersionsDto requestDto, @PathVariable Long questionId) {
        ResponseListDto<ResponseQuestionVersionsDto> responseDto = questionService.getQuestionVersions(requestDto, questionId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 질문 수정(버전 업) (#16)
    @PostMapping("/{questionId}")
    public ResponseEntity<Void> updateQuestion(@RequestBody RequestUpdateQuestionDto requestDto, @PathVariable Long questionId) {
        questionService.updateQuestion(requestDto, questionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
