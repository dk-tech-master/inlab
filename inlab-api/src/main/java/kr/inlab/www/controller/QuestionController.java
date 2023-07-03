package kr.inlab.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.service.QuestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

	private final QuestionService questionService;

	@PostMapping
	public ResponseEntity createQuestion(@RequestBody RequestCreateQuestionDto requestDto) {
		questionService.createQuestion(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
