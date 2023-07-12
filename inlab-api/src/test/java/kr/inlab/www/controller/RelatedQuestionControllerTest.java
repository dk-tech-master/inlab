package kr.inlab.www.controller;

import java.nio.charset.StandardCharsets;

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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class RelatedQuestionControllerTest {

	// @Autowired
	// private MockMvc mockMvc;
	//
	// @Autowired
	// private ObjectMapper objectMapper;
	//
	// @Transactional
	// @Test
	// void 꼬리질문_생성_테스트() throws Exception {
	// 	//given
	// 	RequestCreateRelatedQuestionDto requestDto = RequestCreateRelatedQuestionDto.builder()
	// 		.headQuestionId()
	// 		.tailQuestionId()
	// 		.build();
	// 	String json = objectMapper.writeValueAsString(requestDto);
	//
	// 	// when-then
	// 	mockMvc.perform(post("/api/related-questions")
	// 			.header(HttpHeaders.AUTHORIZATION, "jwt token")
	// 			.accept(MediaType.APPLICATION_JSON)
	// 			.characterEncoding(StandardCharsets.UTF_8)
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(json))
	// 		.andDo(print())
	// 		.andExpect()
	// }
}
