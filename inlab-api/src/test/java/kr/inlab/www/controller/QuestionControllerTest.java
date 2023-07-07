package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
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
				)
			);
	}

}