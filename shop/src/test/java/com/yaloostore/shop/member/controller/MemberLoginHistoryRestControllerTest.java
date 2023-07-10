package com.yaloostore.shop.member.controller;

import com.yaloostore.shop.member.common.GenderCode;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(MemberLoginHistoryRestController.class)
@AutoConfigureRestDocs
@Slf4j
class MemberLoginHistoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberLoginHistoryService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addLoginHistory() throws Exception {
        Long id = 1L;
        String loginId = "test";
        LocalDate now = LocalDate.now();

        MemberLoginHistoryResponse loginHistoryResponse = new MemberLoginHistoryResponse(id,id,loginId,now);
        given(service.addLoginHistory(loginId)).willReturn(loginHistoryResponse);

        ResultActions perform = mockMvc.perform(post("/api/service/members/add/loginHistory/{loginId}", loginId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        perform.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
}