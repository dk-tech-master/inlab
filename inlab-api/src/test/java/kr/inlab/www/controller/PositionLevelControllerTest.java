package kr.inlab.www.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.util.List;
import kr.inlab.www.dto.common.PositionAndLevelInfo;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.dto.request.RequestUpdatePositionLevelDto;
import kr.inlab.www.dto.request.RequestUpdatePositionLevelDto.RequestUpdatePositionLevelDtoBuilder;
import kr.inlab.www.service.PositionLevelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class PositionLevelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Principal principal;

    @Test
    @Transactional
    @WithMockUser(username = "jwoo1016@naver.com", authorities = {"ROLE_ADMIN"})
    void getPositionLevel() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/position_level")
                    .param("userId", "1")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imp3b28xMDE2QG5hdmVyLmNvbSIsInJvbGVzIjpbIlJPTEVfR1VFU1QiXSwiZXhwIjoxNjk3MDEwMzE0LCJpYXQiOjE2ODgzNzAzMTR9.OvbpnBc3rL51hI26yLYiJMp-sTma_wnVHmL2vyn4Fiw"))
            .andExpect(status().isOk());
        resultActions.andDo(document("get-position-level",
            requestHeaders(headerWithName("Authorization").description("JWT Token")),
            responseFields(
                fieldWithPath("positionIdAndLevelIds").description("부여된 권한 정보"),
                fieldWithPath("positionIdAndLevelIds[].positionId").description("직무의 ID"),
                fieldWithPath("positionIdAndLevelIds[].positionName").description("직무의 이름"),
                fieldWithPath("positionIdAndLevelIds[].levelId").description("레벨의 ID"),
                fieldWithPath("positionIdAndLevelIds[].levelName").description("레벨의 이름")
            )
        ));
    }

    @Test
    @Transactional
    void updatePositionLevel() throws Exception {
        List<PositionAndLevelInfo> positionAndLevelInfos = List.of(
            PositionAndLevelInfo.builder().positionId(1).levelId(1).build(),
            PositionAndLevelInfo.builder().positionId(1).levelId(2).build(),
            PositionAndLevelInfo.builder().positionId(1).levelId(3).build()
        );

        RequestUpdatePositionLevelDto requestDto = RequestUpdatePositionLevelDto.builder()
            .userId(1L)
            .positionAndLevelInfos(positionAndLevelInfos)
            .build();
        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.put("/api/position_level")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
                    .header(HttpHeaders.AUTHORIZATION, "jwt token"))
            .andExpect(status().isOk());
        resultActions.andDo(document("update-position-level",
                requestHeaders(headerWithName("Authorization").description("JWT Token")),
            requestFields(
                fieldWithPath("userId").description("권한 변경할 회원의 id"),
                fieldWithPath("positionAndLevelInfos").description("부여할 권한 정보"),
                fieldWithPath("positionAndLevelInfos[].positionId").description("직무의 ID"),
                fieldWithPath("positionAndLevelInfos[].positionName").description("직무의 이름"),
                fieldWithPath("positionAndLevelInfos[].levelId").description("레벨의 ID"),
                fieldWithPath("positionAndLevelInfos[].levelName").description("레벨의 이름")
            )
        ));
    }
}