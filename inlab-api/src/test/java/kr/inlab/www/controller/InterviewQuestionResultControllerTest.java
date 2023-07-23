package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.request.RequestUpdateChecklistResultDto;
import kr.inlab.www.dto.request.RequestUpdateCommentDto;
import kr.inlab.www.dto.request.RequestUpdateInterviewQuestionResultDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class InterviewQuestionResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void 면접질문결과_수정() throws Exception {
        List<RequestUpdateChecklistResultDto> list = new ArrayList<>();
        list.add(RequestUpdateChecklistResultDto.builder()
                .checklistResultId(505L)
                .isChecked(YesNo.Y)
                .build());
        list.add(RequestUpdateChecklistResultDto.builder()
                .checklistResultId(506L)
                .isChecked(YesNo.Y)
                .build());
        list.add(RequestUpdateChecklistResultDto.builder()
                .checklistResultId(507L)
                .isChecked(YesNo.Y)
                .build());
        RequestUpdateInterviewQuestionResultDto requestDto = RequestUpdateInterviewQuestionResultDto.builder()
                .commentDto(RequestUpdateCommentDto.builder()
                        .commentId(167L)
                        .content("정말 좋은 답변이네요.")
                        .build())
                .checklistResultDtoList(list)
                .build();

        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(patch("/api/interview-question-result")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "update-interview-question-result",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestFields(
                                fieldWithPath("commentDto").description("수정할 코멘트 데이터를 담은 DTO"),
                                fieldWithPath("commentDto.commentId").description("코멘트 ID"),
                                fieldWithPath("commentDto.content").description("코멘트 내용"),
                                fieldWithPath("checklistResultDtoList[]").description("수정할 체크리스트 데이터를 담은 리스트 DTO"),
                                fieldWithPath("checklistResultDtoList[].checklistResultId").description("체크리스트 결과 ID"),
                                fieldWithPath("checklistResultDtoList[].isChecked").description("체크여부")
                        )
                ));
    }
}