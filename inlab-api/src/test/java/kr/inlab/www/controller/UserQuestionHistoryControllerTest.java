package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class UserQuestionHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 질문열람기록_리스트_조회() throws Exception {
        mockMvc.perform(get("/api/user-question-history")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-user-question-history-list",
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestParameters(
                                parameterWithName("page").optional().description("요청 페이지(default=0)"),
                                parameterWithName("pageSize").optional().description("페이지 크기(default=10)"),
                                parameterWithName("sortDirection").optional().description("정렬 방식(default=DESC)"),
                                parameterWithName("column").optional().description("정렬 기준 컬럼"),
                                parameterWithName("keyword").optional().description("검색어(면접관 이름, 질문 제목)")
                        ),
                        responseFields(
                                fieldWithPath("responseList[]").description("질문 열람기록 리스트"),
                                fieldWithPath("responseList[].questionHistoryId").description("질문 열람기록 ID"),
                                fieldWithPath("responseList[].questionTitle").description("질문 제목"),
                                fieldWithPath("responseList[].questionVersion").description("질문 버전"),
                                fieldWithPath("responseList[].nickname").description("면접관 이름"),
                                fieldWithPath("responseList[].readingTime").description("열람 일자"),
                                fieldWithPath("pagingUtil.totalElements").description("총 요소 수"),
                                fieldWithPath("pagingUtil.totalPages").description("총 페이지 수"),
                                fieldWithPath("pagingUtil.pageNumber").description("현재 페이지 번호"),
                                fieldWithPath("pagingUtil.pageSize").description("페이지당 항목 개수"),
                                fieldWithPath("pagingUtil.totalPageGroups").description("총 페이지 그룹 수"),
                                fieldWithPath("pagingUtil.pageGroupSize").description("페이지 그룹당 페이지 개수"),
                                fieldWithPath("pagingUtil.pageGroup").description("현재 페이지 그룹"),
                                fieldWithPath("pagingUtil.startPage").description("시작 페이지"),
                                fieldWithPath("pagingUtil.endPage").description("끝 페이지"),
                                fieldWithPath("pagingUtil.existPrePageGroup").description("이전 페이지 그룹 존재 여부"),
                                fieldWithPath("pagingUtil.existNextPageGroup").description("다음 페이지 그룹 존재 여부")
                        )
                ));
    }
}