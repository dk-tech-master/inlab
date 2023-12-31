package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.dto.request.RequestCreateInterviewQuestionDto;
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

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class InterviewQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void 면접질문_리스트_생성_테스트() throws Exception {
        RequestCreateInterviewQuestionDto requestDto = RequestCreateInterviewQuestionDto.builder()
                .interviewId(1L)
                .questionId(1L)
                .questionVersionId(1L)
                .build();
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/interview/questions")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(
                                "create-interview-question",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(headerWithName("Authorization").description("JWT Token")),
                                requestFields(
                                        fieldWithPath("interviewId").description("면접 ID"),
                                        fieldWithPath("questionId").description("질문 ID"),
                                        fieldWithPath("questionVersionId").description("질문 버전 ID")
                                )
                        )
                );
    }

    @Test
    void 면접질문_리스트_조회_테스트() throws Exception {
        mockMvc.perform(get("/api/interview/questions/{interviewId}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-interview-question",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        pathParameters(parameterWithName("interviewId").description("면접 Id")),
                        responseFields(
                                fieldWithPath("[].interviewQuestionId").description("면접질문 리스트 Id"),
                                fieldWithPath("[].levelId").description("난이도 ID"),
                                fieldWithPath("[].levelName").description("질문 난이도 이름"),
                                fieldWithPath("[].questionTitle").description("질문 제목"),
                                fieldWithPath("[].questionLevelName").description("질문 난이도 이름"),
                                fieldWithPath("[].positionName").description("직무 이름"),
                                fieldWithPath("[].questionTypeName").description("질문 유형 이름"),
                                fieldWithPath("[].version").description("버전")
                        ))
                );
    }

    @Test
    @Transactional
    void 면접질문_리스트_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/api/interview/questions/{interviewQuestionId}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-interview-question",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        pathParameters(parameterWithName("interviewQuestionId").description("면접 질문 ID path variable"))
                ));
    }


    @Test
    @Transactional
    void 면접질문_리스트_질문_상세() throws Exception {
        mockMvc.perform(get("/api/interview/questions/detail/{interviewQuestionId}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-interview-questions-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        pathParameters(parameterWithName("interviewQuestionId").description("면접 질문의 Id")),
                        responseFields(
                                fieldWithPath("questionTitle").description("질문 제목"),
                                fieldWithPath("questionTypeName").description("질문 유형 이름"),
                                fieldWithPath("positionName").description("직무 명"),
                                fieldWithPath("questionLevelName").description("난이도 명"),
                                fieldWithPath("version").description("질문의 해당 버전"),
                                fieldWithPath("checklist[].checklistId").description("체크리스트 id"),
                                fieldWithPath("checklist[].content").description("체크리스트 내용")

                        ))
                );
    }
}