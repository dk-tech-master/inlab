package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.dto.request.RequestCheckVerificationCode;
import kr.inlab.www.service.VerificationCodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class VerificationCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VerificationCodeService verificationCodeService;

    private final static String EMAIL = "jwoo1016@gmail.com";
    private final static String VERIFICATION_CODE = "verificationCode";

    @Test
    @Transactional
    void createVerificationCode_shouldSendEmailAndReturnCreatedStatus() throws Exception {

        when(verificationCodeService.createVerificationCode(EMAIL))
                .thenReturn("verificationCode");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/verification_code")
                        .param("email", EMAIL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // Assert
        resultActions.andDo(
                document("create-verification-code",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("email").description("이메일")
                        )
                )
        );
    }

    @Test
    @Transactional
    void createVerificationCodeForUpdate_shouldSendEmailAndReturnCreatedStatus() throws Exception {

        when(verificationCodeService.createVerificationCodeForUpdate(EMAIL))
                .thenReturn("verificationCode");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/verification_code")
                        .param("email", EMAIL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // Assert
        resultActions.andDo(
                document("update-verification-code",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("email").description("이메일")
                        )
                )
        );
    }

    @Test
    @Transactional
    void checkVerificationCode_shouldAddTokenToResponseHeader() throws Exception {

        RequestCheckVerificationCode requestDto = new RequestCheckVerificationCode(EMAIL, VERIFICATION_CODE);

        // Act
        when(verificationCodeService.checkVerificationCode(any(RequestCheckVerificationCode.class)))
                .thenReturn(true);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/verification_code/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // Assert
        resultActions.andDo(document("check-verification-code",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("verificationCode").description("인증번호")
                ),
                responseHeaders(
                        headerWithName("email").description("이메일 인증을 하면 발행되는 이메일 정보가 들어있는 jwt 토큰")
                        // 필요한 만큼 토큰 정보를 추가하세요
                )
        ));
    }
}
