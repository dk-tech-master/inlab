package kr.inlab.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionDto;
import kr.inlab.www.dto.response.RequestQuestionVersionsDto;
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
	public ResponseEntity<ResponseGetQuestionDto> getQuestion(@PathVariable Long questionId) {
		ResponseGetQuestionDto responseDto = questionService.getQuestion(questionId);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	// 해당 질문에 대한 질문 버전 전체 조회 (#13)
	@GetMapping("/{questionId}/question_versions")
	public ResponseEntity<ResponseListDto<ResponseQuestionVersionsDto>> getQuestionVersions(@ModelAttribute RequestQuestionVersionsDto requestDto, @PathVariable Long questionId) {
		ResponseListDto<ResponseQuestionVersionsDto> responseDto = questionService.getQuestionVersions(requestDto, questionId);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	// 질문 수정(버전 업) (#16)
	@PatchMapping("/{questionId}")
	public ResponseEntity<Void> updateQuestion(@RequestBody RequestUpdateQuestionDto requestDto, @PathVariable Long questionId) {
		questionService.updateQuestion(requestDto, questionId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 해당 질문에 대한 꼬리 질문 등록 (#17)
	@PostMapping("/{questionId}/related_questions")
	public ResponseEntity<Void> createRelatedQuestion(@RequestBody RequestCreateRelatedQuestionDto requestDto, @PathVariable Long questionId) {
		questionService.createRelatedQuestion(requestDto, questionId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 해당 질문에 대한 꼬리 질문 전체 조회 (#17)
	// @GetMapping("/{questionId}/related_questions")
	// public ResponseEntity<ResponseRelatedQuestions> getRelatedQuestions() {
	// 	return ResponseEntity.status(HttpStatus.OK).body();
	// }

	// 해당 질문에 대한 꼬리 질문 삭제 (#17)
	// @DeleteMapping("/{questionId}/related_questions")
	// public ResponseEntity<Void> deleteRelatedQuestions() {
	// 	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	// }

}
