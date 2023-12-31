package kr.inlab.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseListWithQuestionDto;
import kr.inlab.www.service.RelatedQuestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/related-questions")
public class RelatedQuestionController {

	private final RelatedQuestionService relatedQuestionService;

	// 해당 질문에 대한 꼬리 질문 등록 (#17)
	@PostMapping
	public ResponseEntity<Void> createRelatedQuestion(@RequestBody RequestCreateRelatedQuestionDto requestDto) {
		relatedQuestionService.createRelatedQuestion(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 해당 질문에 대한 꼬리 질문 전체 조회 (#17)
	@GetMapping
	public ResponseEntity<ResponseListWithQuestionDto<ResponseGetRelatedQuestionsDto>> getRelatedQuestions(
		@ModelAttribute RequestRelatedQuestionsDto requestDto) {
		ResponseListWithQuestionDto<ResponseGetRelatedQuestionsDto> responseDto = relatedQuestionService.getRelatedQuestions(
			requestDto);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	// 해당 질문에 대한 꼬리 질문 삭제 (#17)
	@DeleteMapping("/{relatedQuestionId}")
	public ResponseEntity<Void> deleteRelatedQuestion(@PathVariable Long relatedQuestionId) {
		relatedQuestionService.deleteRelatedQuestion(relatedQuestionId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
