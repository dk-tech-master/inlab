package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.dto.common.RequestTestRestDocsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
//@WebMvcTest(controllers = TestDocController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class TestDocControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void REST_DOCS_GET_테스트() throws Exception {
        mockMvc.perform(get("/api/test-rest-docs/{testPath}?testParam=queryparam", "path")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-test-rest-docs",
                        pathParameters(parameterWithName("testPath").description("test path variable")),
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestParameters(parameterWithName("testParam").description("test query parameter")),
                        responseFields(fieldWithPath("result").description("path variable과 query parameter의 결과")))
                );
    }

    @Test
    void REST_DOCS_POST_테스트() throws Exception {
        RequestTestRestDocsDto requestDto = new RequestTestRestDocsDto("data1", "data2", "data3");
        String json = objectMapper.writeValueAsString(requestDto);

        System.out.println(json);

        mockMvc.perform(post("/api/test-rest-docs")
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(
                        "post-test-rest-docs",
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestFields(
                                fieldWithPath("testData1").description("테스트 데이터1"),
                                fieldWithPath("testData2").description("테스트 데이터2"),
                                fieldWithPath("testData3").description("테스트 데이터3")
                        ),
                        responseFields(fieldWithPath("result").description("요청 Body의 테스트 데이터 1, 2, 3의 결과"))
                        )
                );
    }
}