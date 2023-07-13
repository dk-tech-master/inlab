package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.request.RequestUpdateChecklistResultDto;
import kr.inlab.www.dto.request.RequestUpdateChecklistResultDto.ChecklistResultDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class ChecklistResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void 체크리스트_수정() throws Exception {
        ChecklistResultDto checklist1 = ChecklistResultDto.builder()
                .checklistResultId(55L)
                .isChecked(YesNo.N)
                .build();
        List<ChecklistResultDto> checklistList = new ArrayList<>();
        checklistList.add(checklist1);
        RequestUpdateChecklistResultDto requestDto = RequestUpdateChecklistResultDto.builder()
                .checklistResultDtoList(checklistList)
                .build();
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(patch("/api/checklist-result", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                                "update-checklist-result",
                                requestHeaders(headerWithName("Authorization").description("JWT Token")),
                                requestFields(
                                        fieldWithPath("checklistResultDtoList[]").description("수정될 체크리스트 결과가 담긴 리스트"),
                                        fieldWithPath("checklistResultDtoList[].checklistResultId").description("체크리스트 결과 ID"),
                                        fieldWithPath("checklistResultDtoList[].isChecked").description("체크여부")
                                ),
                                responseFields(
                                        fieldWithPath("[].checklistResultId").description("체크리스트 결과 ID"),
                                        fieldWithPath("[].isChecked").description("수정된 체크 여부")
                                )
                        )
                );
    }
}