package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionDto;

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
import java.util.Arrays;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class QuestionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional
	@Test
	void 질문_생성_테스트() throws Exception {
		//given
		RequestCreateQuestionDto requestDto = RequestCreateQuestionDto.builder()
			.positionId(1)
			.questionTypeId(1)
			.questionLevelId(1)
			.title("테스트 질문 제목")
			.version(1)
			.checklists(Arrays.asList("체크리스트1", "체크리스트2", "체크리스트3"))
			.build();
		String json = objectMapper.writeValueAsString(requestDto);

		// when-then
		mockMvc.perform(post("/api/questions")
				.header(HttpHeaders.AUTHORIZATION, "jwt token")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isCreated())
			.andDo(document(
				"create-question",
				requestHeaders(headerWithName("Authorization").description("JWT Token")),
				requestFields(
					fieldWithPath("positionId").description("질문의 position id"),
					fieldWithPath("questionTypeId").description("질문의 question type id"),
					fieldWithPath("questionLevelId").description("질문의 question level id"),
					fieldWithPath("title").description("질문의 제목"),
					fieldWithPath("version").description("질문의 버전"),
					fieldWithPath("checklists").description("질문의 체크리스트")
				)
			));
	}

	@Transactional
	@Test
	void 질문_전체_조회_테스트() throws Exception {
		// given
		RequestQuestionsDto requestDto = RequestQuestionsDto.builder()
			.positionId(1)
			.questionTypeId(1)
			.questionLevelId(1)
			.titleKeyword("질문")
			.build();
		String json = objectMapper.writeValueAsString(requestDto);

		// when-then
		mockMvc.perform(get("/api/questions")
				.header(HttpHeaders.AUTHORIZATION, "jwt token")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document(
				"get-questions",
				requestHeaders(headerWithName("Authorization").description("JWT Token")),
				requestParameters(
					parameterWithName("page").optional().description("현재 페이지 (Default: 0)"),
					parameterWithName("pageSize").optional().description("페이지당 개수 (Default: 10)"),
					parameterWithName("sortDirection").optional().description("정렬 방식 (Default: DESC)"),
					parameterWithName("column").optional().description("정렬 기준 컬럼 Default: null"),
					parameterWithName("positionId").optional().description("직무 ID"),
					parameterWithName("questionTypeId").optional().description("질문 유형 ID"),
					parameterWithName("questionLevelId").optional().description("질문 난이도 ID"),
					parameterWithName("titleKeyword").optional().description("질문 제목 키워드")
				),
				responseFields(
					fieldWithPath("responseList[].title").description("질문 제목"),
					fieldWithPath("responseList[].questionTypeId").description("질문 유형 ID"),
					fieldWithPath("responseList[].questionTypeName").description("질문 유형 이름"),
					fieldWithPath("responseList[].positionId").description("직무 ID"),
					fieldWithPath("responseList[].positionName").description("직무 이름"),
					fieldWithPath("responseList[].questionLevelId").description("질문 난이도 ID"),
					fieldWithPath("responseList[].questionLevelName").description("질문 난이도 이름"),
					fieldWithPath("responseList[].version").description("질문 버전"),
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
	void 질문_상세_조회_테스트() throws Exception {
		// given
		Long questionId = 10L;

		// when-then
		mockMvc.perform(get("/api/questions/{questionId}", questionId)
				.header(HttpHeaders.AUTHORIZATION, "jwt token")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document(
				"get-question",
				requestHeaders(headerWithName("Authorization").description("JWT Token")),
				pathParameters(parameterWithName("questionId").description("조회할 질문의 ID")),
				responseFields(
					fieldWithPath("title").description("질문의 제목"),
					fieldWithPath("questionTypeId").description("질문 유형의 ID"),
					fieldWithPath("questionTypeName").description("질문 유형의 이름"),
					fieldWithPath("positionId").description("직무의 ID"),
					fieldWithPath("positionName").description("직무의 이름"),
					fieldWithPath("questionLevelId").description("질문 난이도의 ID"),
					fieldWithPath("questionLevelName").description("질문 난이도의 이름"),
					fieldWithPath("version").description("질문의 버전"),
					fieldWithPath("checklists[]").description("체크리스트")
				)
			));
	}

	@Transactional
	@Test
	void 질문_수정_테스트() throws Exception {
		// given
		Long questionId = 10L;
		RequestUpdateQuestionDto requestDto = RequestUpdateQuestionDto.builder()
			.positionId(1)
			.questionTypeId(1)
			.questionLevelId(1)
			.title("수정된 테스트 질문 제목")
			.checklists(Arrays.asList("수정된 체크리스트1", "수정된 체크리스트2", "수정된 체크리스트3"))
			.build();
		String json = objectMapper.writeValueAsString(requestDto);

		// when-then
		mockMvc.perform(post("/api/questions/{questionId}", questionId)
				.header(HttpHeaders.AUTHORIZATION, "jwt token")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isCreated())
			.andDo(document(
					"update-question",
					requestHeaders(headerWithName("Authorization").description("JWT Token")),
					pathParameters(parameterWithName("questionId").description("수정할 질문의 ID")),
					requestFields(
						fieldWithPath("positionId").description("질문의 position id"),
						fieldWithPath("questionTypeId").description("질문의 question type id"),
						fieldWithPath("questionLevelId").description("질문의 question level id"),
						fieldWithPath("title").description("수정된 질문의 제목"),
						fieldWithPath("checklists").description("수정된 질문의 체크리스트")
					)
				)
			);
	}
}