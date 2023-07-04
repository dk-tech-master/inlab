package kr.inlab.www.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class VerificationCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void createVerificationCode_shouldSendEmailAndReturnCreatedStatus() throws Exception {
        // Arrange
        String email = "test@example.com";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/verification_code")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        // Assert
        resultActions.andDo(
            document("create-verification-code",
                requestParameters(
                    parameterWithName("email").description("이메일")
                )
            )
        );
    }

    @Test
    @Transactional
    void createVerificationCodeForUpdate_shouldSendEmailAndReturnCreatedStatus() throws Exception {
        // Arrange
        String email = "jwoo1016@naver.com";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/verification_code")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        // Assert
        resultActions.andDo(
            document("create-verification-code",
                requestParameters(
                    parameterWithName("email").description("이메일")
                )
            )
        );
    }

}
