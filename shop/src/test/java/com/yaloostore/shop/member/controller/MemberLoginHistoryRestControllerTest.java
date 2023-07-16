package com.yaloostore.shop.member.controller;

import com.yaloostore.shop.member.common.GenderCode;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.dummy.MembershipDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.basic.MemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.MemberLoginHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentRequest;
import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentsResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(MemberLoginHistoryRestController.class)
@AutoConfigureRestDocs
class MemberLoginHistoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberLoginHistoryService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser
    void addLoginHistory() throws Exception {
        Long id = 1L;
        String loginId = "test";
        LocalDate now = LocalDate.now();

        MemberLoginHistoryResponse loginHistoryResponse = new MemberLoginHistoryResponse(id,id,loginId,now);
        given(service.addLoginHistory(loginId)).willReturn(loginHistoryResponse);


        //when
        ResultActions perform = mockMvc.perform(post("/api/service/members/add/loginHistory/{loginId}", loginId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));


        //then
        perform.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.historyId", equalTo(loginHistoryResponse.getHistoryId().intValue())))
                .andExpect(jsonPath("$.data.memberId", equalTo(loginHistoryResponse.getMemberId().intValue())))
                .andExpect(jsonPath("$.data.loginId", equalTo(loginHistoryResponse.getLoginId())))
                .andExpect(jsonPath("$.data.loginTime", equalTo(loginHistoryResponse.getLoginTime().toString())));


        //spring rest docs (api 자동화)
        perform.andDo(document(
                "add-member-login-history",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("loginId").description("회원 로그인 아이디")),
                responseFields(
                        fieldWithPath("success")
                                .type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("data.historyId")
                                .type(JsonFieldType.NUMBER)
                                .description("회원 로그인 기록 pk"),
                        fieldWithPath("data.memberId")
                                .type(JsonFieldType.NUMBER)
                                .description("회원 pk"),
                        fieldWithPath("data.loginId")
                                .type(JsonFieldType.STRING)
                                .description("회원의 로그인 아이디"),
                        fieldWithPath("data.loginTime")
                                .type(JsonFieldType.STRING)
                                .description("회원의 로그인 시간"),
                        fieldWithPath("status")
                                .type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("errorMessages")
                                .type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional())
                ));
    }

    @Test
    @WithMockUser
    void getMemberByLoginHistory() throws Exception {
        Long id = 1L;
        String loginId = "test";
        LocalDate today = LocalDate.now();

        MemberLoginHistoryResponse loginHistoryResponse = new MemberLoginHistoryResponse(id,id,loginId,today.minusYears(1));
        given(service.addLoginHistory(loginId)).willReturn(loginHistoryResponse);

        List<MemberIdResponse> list = new ArrayList<>();
        MemberIdResponse memberIdResponse = new MemberIdResponse(loginHistoryResponse.getMemberId());
        list.add(memberIdResponse);

        given(service.findMemberByLoginHistory(today)).willReturn(list);

        //when
        ResultActions perform = mockMvc.perform(get("/api/service/members/loginHistory/{today}", today)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].memberId", equalTo(memberIdResponse.getMemberId().intValue())));

        //spring rest docs (api 자동화)
        perform.andDo(document(
                "get-member-by-login-history-one-years-ago-member",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("today").description("오늘 날짜")),
                responseFields(
                        fieldWithPath("success")
                                .type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("data.[].memberId")
                                .type(JsonFieldType.NUMBER)
                                .description("지난 1년간 로그인한 기록이 없는 회원의 pk"),
                        fieldWithPath("status")
                                .type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("errorMessages")
                                .type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional())
        ));





    }
}