package kr.inlab.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;
import kr.inlab.www.service.QuestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

	private final QuestionService questionService;

	// 질문 등록 (#15)
	@PostMapping
	public ResponseEntity createQuestion(@RequestBody RequestCreateQuestionDto requestDto) {
		questionService.createQuestion(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 질문 전체 조회 (#12)
	// 데이터 : title, position, question_type, question_level, version(isLatest = Y 값만),
	// @GetMapping
	// public ResponseEntity getAllQuestions

	// 질문 검색 조회 (#12)
	// 데이터 : title, position, question_type, question_level, version(isLatest = Y 값만),

	// 해당 질문에 대한 질문 버전 전체 조회 (#13)
	// 데이터 : title, position, question_type, question_level, version(전부),

	// 질문 상세 조회 (#14)
	@GetMapping("/{questionId}")
	public ResponseEntity<ResponseGetQuestionDto> getQuestion(@PathVariable Long questionId) {
		ResponseGetQuestionDto responseDto = questionService.getQuestion(questionId);

		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	// 질문 수정 (#16) - 버전 카운팅도 있어야됨

}
