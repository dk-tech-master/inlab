package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.ChecklistResultDto;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.CommentDto;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.InterviewAnswerDto;
import kr.inlab.www.dto.request.RequestCreateInterviewResultDto.InterviewQuestionResultDto;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class InterviewResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    @Test
    void 면접결과_생성_테스트() throws Exception {
        List<ChecklistResultDto> checklistResultDtoList1 = new ArrayList<>();
        checklistResultDtoList1.add(ChecklistResultDto.builder()
                .checklistId(1L)
                .isChecked(YesNo.Y)
                .build());
        checklistResultDtoList1.add(ChecklistResultDto.builder()
                .checklistId(2L)
                .isChecked(YesNo.Y)
                .build());
        checklistResultDtoList1.add(ChecklistResultDto.builder()
                .checklistId(3L)
                .isChecked(YesNo.Y)
                .build());

        List<ChecklistResultDto> checklistResultDtoList2 = new ArrayList<>();
        checklistResultDtoList2.add(ChecklistResultDto.builder()
                .checklistId(4L)
                .isChecked(YesNo.N)
                .build());
        checklistResultDtoList2.add(ChecklistResultDto.builder()
                .checklistId(5L)
                .isChecked(YesNo.N)
                .build());
        checklistResultDtoList2.add(ChecklistResultDto.builder()
                .checklistId(6L)
                .isChecked(YesNo.N)
                .build());

        List<ChecklistResultDto> checklistResultDtoList3 = new ArrayList<>();
        checklistResultDtoList3.add(ChecklistResultDto.builder()
                .checklistId(7L)
                .isChecked(YesNo.Y)
                .build());
        checklistResultDtoList3.add(ChecklistResultDto.builder()
                .checklistId(8L)
                .isChecked(YesNo.Y)
                .build());
        checklistResultDtoList3.add(ChecklistResultDto.builder()
                .checklistId(9L)
                .isChecked(YesNo.N)
                .build());

        List<InterviewQuestionResultDto> interviewQuestionResultDtoList = new ArrayList<>();
        interviewQuestionResultDtoList.add(InterviewQuestionResultDto.builder()
                .interviewQuestionId(1L)
                .commentDto(CommentDto.builder()
                        .content("좋은 답변입니다.")
                        .build())
                .interviewAnswerDto(InterviewAnswerDto.builder()
                        .content("OOP에 대해 설명했습니다.")
                        .build())
                .checklistResultDtoList(checklistResultDtoList1)
                .build());

        interviewQuestionResultDtoList.add(InterviewQuestionResultDto.builder()
                .interviewQuestionId(2L)
                .commentDto(CommentDto.builder()
                        .content("답변을 제대로 못했습니다.")
                        .build())
                .interviewAnswerDto(InterviewAnswerDto.builder()
                        .content("질문1에 대해 모르겠습니다.")
                        .build())
                .checklistResultDtoList(checklistResultDtoList2)
                .build());

        interviewQuestionResultDtoList.add(InterviewQuestionResultDto.builder()
                .interviewQuestionId(3L)
                .commentDto(CommentDto.builder()
                        .content("체크리스트 한 개 빼고 답변했습니다.")
                        .build())
                .interviewAnswerDto(InterviewAnswerDto.builder()
                        .content("질문2에 대해 둘은 알고있고 하나는 모르겠습니다.")
                        .build())
                .checklistResultDtoList(checklistResultDtoList3)
                .build());

        RequestCreateInterviewResultDto requestDto = RequestCreateInterviewResultDto.builder()
                .intervieweeName("김철진")
                .interviewId(1L)
                .interviewQuestionResultDtoList(interviewQuestionResultDtoList)
                .build();

        String json = objectMapper.writeValueAsString(requestDto);

        System.out.println(json);

        mockMvc.perform(post("/api/interview-result")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(
                        "create-interview-result",
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestFields(
                                fieldWithPath("intervieweeName").description("면접자 이름"),
                                fieldWithPath("interviewId").description("면접 결과를 저장하고 싶은 면접 ID"),
                                fieldWithPath("interviewQuestionResultDtoList[]").description("면접 결과가 담긴 DTO 리스트"),
                                fieldWithPath("interviewQuestionResultDtoList[].interviewQuestionId").description("면접 질문 ID"),
                                fieldWithPath("interviewQuestionResultDtoList[].commentDto").description("면접자의 답변에 대한 면접관의 코멘트가 담긴 DTO"),
                                fieldWithPath("interviewQuestionResultDtoList[].commentDto.content").description("코멘트 내용"),
                                fieldWithPath("interviewQuestionResultDtoList[].interviewAnswerDto").description("면접 질문에 대한 면접자의 답변이 담긴 DTO"),
                                fieldWithPath("interviewQuestionResultDtoList[].interviewAnswerDto.content").description("답변 내용"),
                                fieldWithPath("interviewQuestionResultDtoList[].checklistResultDtoList[]").description("질문에 해당하는 평가 체크리스트가 담긴 DTO"),
                                fieldWithPath("interviewQuestionResultDtoList[].checklistResultDtoList[].checklistId").description("체크리스트 ID"),
                                fieldWithPath("interviewQuestionResultDtoList[].checklistResultDtoList[].isChecked").description("체크 여부")
                        )
                ));
    }
}