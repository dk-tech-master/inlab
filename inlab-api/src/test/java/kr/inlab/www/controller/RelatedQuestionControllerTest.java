package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestRelatedQuestionsDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import javax.transaction.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class RelatedQuestionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional
	@Test
	void 꼬리질문_생성_테스트() throws Exception {
		// given
		RequestCreateRelatedQuestionDto requestDto = RequestCreateRelatedQuestionDto.builder()
			.headQuestionId(10L)
			.tailQuestionId(11L)
			.build();
		String json = objectMapper.writeValueAsString(requestDto);

		// when-then
		mockMvc.perform(post("/api/related-questions")
				.header(HttpHeaders.AUTHORIZATION, "jwt token")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isCreated())
			.andDo(document(
				"create-related-question",
				requestHeaders(headerWithName("Authorization").description("JWT Token")),
				requestFields(
					fieldWithPath("headQuestionId").description("질문 ID"),
					fieldWithPath("tailQuestionId").description("꼬리질문 ID")
				)
			));
	}

	@Transactional
	@Test
	void 꼬리질문_조회_테스트() throws Exception {
		// given
		RequestRelatedQuestionsDto requestDto = RequestRelatedQuestionsDto.builder()
			.questionId(10L)
			.build();
		String json = objectMapper.writeValueAsString(requestDto);

		// when-then
		mockMvc.perform(get("/api/related-questions")
				.param("questionId", "10")
				.header(HttpHeaders.AUTHORIZATION, "jwt token")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON))
				// .content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document(
				"get-related-questions",
				requestHeaders(headerWithName("Authorization").description("JWT Token")),
				requestParameters(
					parameterWithName("page").optional().description("현재 페이지 (Default: 0)"),
					parameterWithName("pageSize").optional().description("페이지당 개수 (Default: 10)"),
					parameterWithName("sortDirection").optional().description("정렬 방식 (Default: DESC)"),
					parameterWithName("column").optional().description("정렬 기준 컬럼 Default: null"),
					parameterWithName("questionId").description("질문 ID")
				),
				responseFields(
					fieldWithPath("responseList[]").description("꼬리질문 리스트"),
					fieldWithPath("responseList[].title").description("꼬리질문의 제목"),
					fieldWithPath("responseList[].questionTypeId").description("꼬리질문 유형 ID"),
					fieldWithPath("responseList[].questionTypeName").description("꼬리질문 유형 이름"),
					fieldWithPath("responseList[].positionId").description("직무 ID"),
					fieldWithPath("responseList[].positionName").description("직무 이름"),
					fieldWithPath("responseList[].questionLevelId").description("꼬리질문 난이도 ID"),
					fieldWithPath("responseList[].questionLevelName").description("꼬리질문 난이도 이름"),
					fieldWithPath("responseList[].version").description("꼬리질문 버전"),
					fieldWithPath("pagingUtil.totalElements").description("총 항목 수"),
					fieldWithPath("pagingUtil.totalPages").description("총 페이지 수"),
					fieldWithPath("pagingUtil.pageNumber").description("현재 페이지 번호"),
					fieldWithPath("pagingUtil.pageSize").description("페이지당 항목 개수"),
					fieldWithPath("pagingUtil.totalPageGroups").description("총 페이지 그룹 수"),
					fieldWithPath("pagingUtil.pageGroupSize").description("페이지 그룹당 페이지 개수"),
					fieldWithPath("pagingUtil.pageGroup").description("현재 페이지 그룹"),
					fieldWithPath("pagingUtil.startPage").description("시작 페이지"),
					fieldWithPath("pagingUtil.endPage").description("마지막 페이지"),
					fieldWithPath("pagingUtil.existPrePageGroup").description("이전 페이지 그룹 존재 여부"),
					fieldWithPath("pagingUtil.existNextPageGroup").description("다음 페이지 그룹 존재 여부")
				)
			));
	}

	@Transactional
	@Test
	void 꼬리질문_삭제_테스트() throws Exception {
		// given
		Long relatedQuestionId = 1L;

		// when-then
		mockMvc.perform(delete("/api/related-questions/{relatedQuestionId}", relatedQuestionId)
			.header(HttpHeaders.AUTHORIZATION, "jwt token"))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andDo(document(
				"delete-related-question",
				requestHeaders(headerWithName("Authorization").description("JWT Token")),
				pathParameters(parameterWithName("relatedQuestionId").description("꼬리질문 ID")
				)
			));
	}
}
