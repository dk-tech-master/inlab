package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.dto.request.RequestCreateInterviewDto;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.InterviewRepository;
import kr.inlab.www.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class InterviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    Interview createInterview() {
        User user = userRepository.findById(1L).get();

        Interview interview = Interview.builder()
                .title("면접 테스트")
                .user(user)
                .build();
        interviewRepository.save(interview);
        return interview;
    }

    @Test
    @Transactional
    void 면접_생성_테스트() throws Exception {
        //given
        RequestCreateInterviewDto requestDto = RequestCreateInterviewDto.builder().userId(1L)
                .interviewTitle("면접생성 테스트")
                .build();
        String json = objectMapper.writeValueAsString(requestDto);

        //when-then
        mockMvc.perform(post("/api/interview")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(
                                "create-interview",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(headerWithName("Authorization").description("JWT Token")),
                                requestFields(fieldWithPath("userId").description("로그인한 사용자"),
                                        fieldWithPath("interviewTitle").description("면접 제목"))
                        )
                );
    }

    @Test
    @Transactional
    @WithMockUser(username = "jwoo1016@naver.com", authorities = {"ROLE_ADMIN"})
    void 면접_조회_테스트() throws Exception {
        createInterview();
        mockMvc.perform(get("/api/interview/{userId}", 1L)
                        .param("page", "1")
                        .param("interviewTitle", "")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-interview",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestParameters(
                                parameterWithName("page").description("paging 위한 파리미터"),
                                parameterWithName("interviewTitle").description("interview 검색 조건을 위한 파라미터")),
                        pathParameters(parameterWithName("userId").description("사용자 ID")),
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
                                fieldWithPath("responseList[].interviewId").description("면접의 ID"),
                                fieldWithPath("responseList[].interviewTitle").description("면접의 제목"),
                                fieldWithPath("responseList[].nickname").description("면접관의 이름"),
                                fieldWithPath("responseList[].questionCount").description("면접의 질문 리스트의 질문 수")))
                );
    }

    @Test
    @Transactional
    void 면접제목_수정_테스트() throws Exception {
        Interview interview = createInterview();
        RequestCreateInterviewDto requestDto = RequestCreateInterviewDto.builder()
                .interviewTitle("면접 수정 테스트")
                .build();
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/api/interview/{interviewId}", interview.getInterviewId())
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "put-interview",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        pathParameters(parameterWithName("interviewId").description("면접 Id path variable")),
                        requestFields(fieldWithPath("userId").description("로그인한 사용자"),
                                fieldWithPath("interviewTitle").description("면접 제목")))
                );
    }

    // document 수정
    @Test
    void 면접시작_질문리스트_테스트() throws Exception {
        mockMvc.perform(get("/api/interview/start/{interviewId}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-interview-start-questionList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        pathParameters(parameterWithName("interviewId").description("면접 Id path variable")),
                        responseFields(
                                fieldWithPath("[].questionTitle").description("질문 이름"),
                                fieldWithPath("[].interviewQuestionId").description("면접 질문 리스트 Id"),
                                fieldWithPath("[].questionVersionId").description("면접 질문 버전 Id"),
                                fieldWithPath("[].interviewTitle").description("질문 제목"),
                                fieldWithPath("[].version").description("면접 질문 버전"),
                                fieldWithPath("[].checklistList[].checklistId").description("질문의 체크리스트 id"),
                                fieldWithPath("[].checklistList[].content").description("질문의 체크리스트 내용")
                        )
                ));
    }
}