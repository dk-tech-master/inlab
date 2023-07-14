package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.dto.request.RequestCreateQuestionTypeDto;
import kr.inlab.www.entity.QuestionType;
import kr.inlab.www.repository.QuestionTypeRepository;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class QuestionTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Transactional
    @Test
    void 질문유형_생성_테스트() throws Exception {
        RequestCreateQuestionTypeDto requestDto = RequestCreateQuestionTypeDto.builder().questionTypeName("테스트 유형 생성").build();
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/question-type")
                .header(HttpHeaders.AUTHORIZATION, "jwt token")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(
                                "create-question-type",
                                requestHeaders(headerWithName("Authorization").description("JWT Token")),
                                requestFields(fieldWithPath("questionTypeName").description("유형 명"))
                        )
                );
    }

    @Transactional
    @Test
    void 질문유형_조회_테스트() throws Exception {
        Integer questionTypeId = getTestQuestionTypeId();

        mockMvc.perform(get("/api/question-type")
                .param("page","1")
                .param("questionTypeName","")
                .header(HttpHeaders.AUTHORIZATION, "jwt token")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-question-type",
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestParameters(
                                parameterWithName("page").description("paging 위한 파리미터"),
                                parameterWithName("questionTypeName").description("position 검색 조건을 위한 파라미터")),
                        responseFields(
                                fieldWithPath("pagingUtil.totalElements").description("Total number of elements"),
                                fieldWithPath("pagingUtil.totalPages").description("총 페이지 수"),
                                fieldWithPath("pagingUtil.pageNumber").description("현재 페이지 번호"),
                                fieldWithPath("pagingUtil.pageSize").description("페이지당 항목 개수"),
                                fieldWithPath("pagingUtil.totalPageGroups").description("총 페이지 그룹 수"),
                                fieldWithPath("pagingUtil.pageGroupSize").description("페이지 그룹당 페이지 개수"),
                                fieldWithPath("pagingUtil.pageGroup").description("현재 페이지 그룹"),
                                fieldWithPath("pagingUtil.startPage").description("시작 페이지"),
                                fieldWithPath("pagingUtil.endPage").description("끝 페이지"),
                                fieldWithPath("pagingUtil.existPrePageGroup").description("이전 페이지 그룹 존재 여부"),
                                fieldWithPath("pagingUtil.existNextPageGroup").description("다음 페이지 그룹 존재 여부"),
                                fieldWithPath("responseList[].questionTypeId").description("유형 ID"),
                                fieldWithPath("responseList[].questionTypeName").description("유형 이름"),
                                fieldWithPath("responseList[].questionCount").description("유형에 대한 질문 수")))
                );
    }

    @Transactional
    @Test
    public void 질문유형_수정_테스트() throws Exception {
        Integer questionTypeId = getTestQuestionTypeId();

        RequestCreateQuestionTypeDto requestDto = RequestCreateQuestionTypeDto.builder().questionTypeName("질문 유형 수정").build();
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/api/question-type/{questionTypeId}",questionTypeId)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                                "update-question-type",
                                requestHeaders(headerWithName("Authorization").description("JWT Token")),
                                pathParameters(parameterWithName("questionTypeId").description("유형의 ID path variable")),
                                requestFields(fieldWithPath("questionTypeName").description("유형 명"))
                        )
                );
    }
    @Transactional
    @Test
    void 질문유형_삭제_테스트() throws Exception {
        Integer questionTypeId = getTestQuestionTypeId();

        mockMvc.perform(delete("/api/question-type/{questionTypeId}", questionTypeId)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-question-type",
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        pathParameters(parameterWithName("questionTypeId").description("유형의 ID path variable"))
                ));
    }

    Integer getTestQuestionTypeId() {
        QuestionType questionType = QuestionType.builder()
                .questionTypeName("테스트 질문 유형")
                .build();
       questionTypeRepository.save(questionType);
       return questionType.getQuestionTypeId();
    }
}
