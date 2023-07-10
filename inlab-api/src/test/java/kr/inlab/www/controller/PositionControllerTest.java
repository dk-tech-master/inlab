package kr.inlab.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.repository.PositionRepository;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PositionRepository positionRepository;

    @Test
    @Transactional
    void 직무_생성_테스트() throws Exception {
        RequestPositionNameDto requestDto = RequestPositionNameDto.builder().positionName("테스트 직무 생성").build();
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/position")
                .header(HttpHeaders.AUTHORIZATION, "jwt token")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(
                        "create-position",
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                                requestFields(
                                        fieldWithPath("positionName").description("직무 명")
                                )
                        )
                );
    }

    @Transactional
    @Test
    void 직무_조회_테스트() throws Exception {
        //given
        Integer positionId = getTestPositionId();
        mockMvc.perform(get("/api/position")
                    .param("page","1")
                    .param("positionName","")
                    .header(HttpHeaders.AUTHORIZATION, "jwt token")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-position",
                        requestHeaders(headerWithName("Authorization").description("JWT Token")),
                        requestParameters(
                                parameterWithName("page").description("paging 위한 파리미터"),
                                parameterWithName("positionName").description("position 검색 조건을 위한 파라미터")),
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
                                fieldWithPath("responseList[].positionId").description("직책의 ID"),
                                fieldWithPath("responseList[].positionName").description("직책의 이름"),
                                fieldWithPath("responseList[].questionCount").description("직책에 대한 질문 수")))
                );
    }

    @Test
    @Transactional
    void 직무_삭제_테스트() throws Exception {
        //given
        Integer positionId = getTestPositionId();
        mockMvc.perform(delete("/api/position/{positionId}", positionId)
                    .header(HttpHeaders.AUTHORIZATION, "jwt token")
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-position",
                        pathParameters(parameterWithName("positionId").description("직무의 ID path variable")),
                        requestHeaders(headerWithName("Authorization").description("JWT Token"))
                ));
    }

    @Transactional
    @Test
    void 직무_수정_테스트() throws Exception {
        //given
        Integer positionId = getTestPositionId();
        RequestPositionNameDto requestDto = RequestPositionNameDto.builder().positionName("백앤드").build();
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/api/position/{positionId}", positionId)
                        .header(HttpHeaders.AUTHORIZATION, "jwt token")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                                "put-position",
                                requestHeaders(headerWithName("Authorization").description("JWT Token")),
                                pathParameters(parameterWithName("positionId").description("직무의 ID path variable")),
                                requestFields(
                                        fieldWithPath("positionName").description("직무 명")
                                )
                        )
                );

    }

    Integer getTestPositionId() {
        Position position = Position.builder()
                .positionName("테스트 직무").build();

        positionRepository.save(position);
        return position.getPositionId();
    }
}