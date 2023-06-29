package com.yaloostore.shop.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.member.dto.response.MemberDuplicateDto;
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
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
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

    private final static String PREFIX_CHECK_PATH = "/api/service/members/check";



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

    @DisplayName("해당 닉네임을 가진 회원이 존재하는지 확인하는 컨트롤러 테스트")
    @Test
    @WithMockUser
    void existMemberByNickname() throws Exception {
        //given
        String nickname = "test";
        when(memberService.existMemberByNickname(nickname)).thenReturn(true);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "Nickname/{nickname}", nickname)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(true)));


        verify(memberService, times(1)).existMemberByNickname(nickname);


        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-nickname-success",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("nickname")
                                .description("회원 가입 시 중복 체크 대상 - nickname")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 닉네임 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }
    @DisplayName("해당 닉네임을 가진 회원이 존재하는지 확인하는 컨트롤러 테스트")
    @Test
    @WithMockUser
    void existMemberByNickname_fail() throws Exception {
        //given
        String nickname = "test";
        when(memberService.existMemberByNickname(nickname)).thenReturn(false);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "Nickname/{nickname}", nickname)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(false)));


        verify(memberService, times(1)).existMemberByNickname(nickname);


        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-nickname-fail",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("nickname")
                                .description("회원 가입 시 중복 체크 대상 - nickname")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 닉네임 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }

    @DisplayName("해당 휴대전화 번호를 가진 회원이 존재하는지 확인하는 컨트롤러 테스트 - 존재하는 경우")
    @Test
    @WithMockUser
    void existMemberByPhoneNumber_exist() throws Exception {
        //given
        String phone = "01066669999";
        when(memberService.existMemberByPhoneNumber(phone)).thenReturn(true);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "Phone/{phone}", phone)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(true)));


        verify(memberService, times(1)).existMemberByPhoneNumber(phone);


        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-phone-exist",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("phone")
                                .description("회원 가입 시 중복 체크 대상 - phoneNumber")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 휴대 전화번호 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }@DisplayName("해당 휴대전화 번호를 가진 회원이 존재하는지 확인하는 컨트롤러 테스트 - 존재하지 않는 경우")
    @Test
    @WithMockUser
    void existMemberByPhoneNumber_notExist() throws Exception {
        //given
        String phone = "01066669999";
        when(memberService.existMemberByPhoneNumber(phone)).thenReturn(false);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "Phone/{phone}", phone)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(false)));


        verify(memberService, times(1)).existMemberByPhoneNumber(phone);


        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-phone-not-exist",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("phone")
                                .description("회원 가입 시 중복 체크 대상 - phoneNumber")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 휴대 전화번호 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }
    @DisplayName("해당 이메일 주소를 가진 회원이 존재하는지 확인하는 컨트롤러 테스트 - 존재하는 경우")
    @Test
    @WithMockUser
    void existMemberByEmail_exist() throws Exception {
        //given
        String email = "test@test.com";
        when(memberService.existMemberByEmail(email)).thenReturn(true);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "Email/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(true)));


        verify(memberService, times(1)).existMemberByEmail(email);


        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-email-exist",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("email")
                                .description("회원 가입 시 중복 체크 대상 - email")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 이메일주소 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }
    @DisplayName("해당 이메일 주소를 가진 회원이 존재하는지 확인하는 컨트롤러 테스트 - 존재하지 않는 경우")
    @Test
    @WithMockUser
    void existMemberByEmail_notExist() throws Exception {
        //given
        String email = "test@test.com";
        when(memberService.existMemberByEmail(email)).thenReturn(false);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "Email/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(false)));


        verify(memberService, times(1)).existMemberByEmail(email);


        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-email-not-exist",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("email")
                                .description("회원 가입 시 중복 체크 대상 - email")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 이메일주소 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }
    @DisplayName("해당 로그인아이디를 가진 회원이 존재하는지 확인하는 컨트롤러 테스트 - 존재하는 경우")
    @Test
    @WithMockUser
    void existMemberByLoginId_exist() throws Exception {
        //given
        String loginId = "test";
        when(memberService.existMemberByLoginId(loginId)).thenReturn(true);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "LoginId/{loginId}", loginId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(true)));


        verify(memberService, times(1)).existMemberByLoginId(loginId);


        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-loginId-exist",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("loginId")
                                .description("회원 가입 시 중복 체크 대상 - loginId")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 이메일주소 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }
    @DisplayName("해당 로그인 아이디를 가진 회원이 존재하는지 확인하는 컨트롤러 테스트 - 존재하지 않는 경우")
    @Test
    @WithMockUser
    void existMemberByLoginId_notExist() throws Exception {
        //given
        String loginId = "test";
        when(memberService.existMemberByLoginId(loginId)).thenReturn(false);

        //when
        ResultActions perform = mockMvc.perform(get(PREFIX_CHECK_PATH + "LoginId/{loginId}", loginId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.result", equalTo(false)));


        verify(memberService, times(1)).existMemberByLoginId(loginId);



        //spring rest doc (api 자동화)
        perform.andDo(document(
                "exist-member-by-loginId-not-exist",
                getDocumentRequest(),
                getDocumentsResponse(),
                pathParameters(
                        parameterWithName("loginId")
                                .description("회원 가입 시 중복 체크 대상 - loginId")
                ),
                responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("data.result").type(JsonFieldType.BOOLEAN)
                                .description("회원 이메일주소 중복 여부"),
                        fieldWithPath("errorMessages").type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional()
                )
        ));

    }

}