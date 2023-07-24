package kr.inlab.www.controller;

import javax.websocket.server.PathParam;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.request.RequestGetQuestionTypeByPositionDto;
import kr.inlab.www.dto.request.RequestQuestionTypeDto;
import kr.inlab.www.dto.request.RequestCreateQuestionTypeDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionTypeDto;
import kr.inlab.www.dto.response.ResponseQuestionTypeDto;
import kr.inlab.www.service.QuestionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question-type")
public class QuestionTypeController {

    private final QuestionTypeService questionTypeService;

    @PostMapping
    public ResponseEntity createQuestionType(@RequestBody RequestCreateQuestionTypeDto requestDto) {
        questionTypeService.createQuestionType(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity getQuestionType(RequestQuestionTypeDto requestDto) {
        ResponseListDto<ResponseQuestionTypeDto> responseDto = questionTypeService.getQuestionType(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/category")
    public ResponseEntity getQuestionTypeByPositionId(@ModelAttribute RequestGetQuestionTypeByPositionDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(questionTypeService.getQuestionTypeByPosition(requestDto));
    }

    @PutMapping("/{questionTypeId}")
    public ResponseEntity updateQuestionType(@RequestBody RequestUpdateQuestionTypeDto requestDto, @PathVariable Integer questionTypeId) {
        questionTypeService.updateQuestionType(questionTypeId,requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{questionTypeId}")
    public ResponseEntity deleteQuestionType(@PathVariable Integer questionTypeId) {
        questionTypeService.deleteQuestionType(questionTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
