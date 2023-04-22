package com.yaloostore.shop.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentRequest;
import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentsResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureRestDocs
@WebMvcTest(QueryMemberRestController.class)
class QueryMemberRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    QueryMemberService memberService;



    @DisplayName("n일 후 생일 회원 조회 성공")
    @WithMockUser
    @Test
    void getMemberByBirthMonthDay() throws Exception {
        //given
        int laterDays = 7;
        Long memberId = 1L;

        when(memberService.findMemberIdByLateDay(anyInt())).thenReturn(List.of(new MemberIdResponse(memberId)));

        //when
        ResultActions perform = mockMvc.perform(get("/api/service/members/birthday")
                .with(csrf())
                .queryParam("laterDays", String.valueOf(laterDays))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print());

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data[0].memberId").value(memberId));

        perform.andDo(document(
                "find-member-id-list-by-member-birthday-success",
                getDocumentRequest(),
                getDocumentsResponse(),
                queryParameters(
                        parameterWithName("laterDays")
                                .description("오늘 날짜를 기준으로 생일을 계산할 일수")
                ),
                responseFields(
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER)
                                .description("생일인 회원의 기본키 값")
                )
        ));


    }
}