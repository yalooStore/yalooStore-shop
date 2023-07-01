package com.yaloostore.shop.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.member.common.Grade;
import com.yaloostore.shop.member.dto.request.MemberCreateRequest;
import com.yaloostore.shop.member.dto.request.MemberUpdateRequest;
import com.yaloostore.shop.member.dto.response.MemberCreateResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.dto.response.MemberUpdateResponse;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberAddress;
import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import com.yaloostore.shop.role.common.RoleType;
import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.member.exception.NotFoundMemberException;
import com.yaloostore.shop.member.service.inter.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentRequest;
import static com.yaloostore.shop.docs.RestApiDocumentation.getDocumentsResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberRestController.class)
@AutoConfigureRestDocs
@Slf4j
class MemberRestControllerTest {

    private final String NAME = "yaloo";
    private final String NICKNAME = "yaloo";
    private final String ID = "yaloo";
    private final String PHONENUMBER = "01000009999";
    private final String INVALID_PASSWORD = "";
    private final String VALID_PASSWORD = "asdad122ed@";
    private final String BIRTH = "19960320";
    private final String EMAIL = "test@test.com";
    private final String GENDER = "FEMALE";
    private final String ROLE_USER = "ROLE_USER";

    private MemberCreateRequest request;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private QueryMemberService queryMemberService;
    private Member member;
    private MemberCreateResponse createResponse;
    private MemberUpdateResponse updateResponse;
    private MemberSoftDeleteResponse deleteResponse;

    private Role role;
    private Membership membership;


    @BeforeEach
    void setUp() {
        Long memberId = 1L;
        Long membershipId = 1L;
        membership = Membership.builder()
                .membershipId(membershipId)
                .grade(Grade.WHITE)
                .membershipStandardAmount(0L)
                .membershipPoint(1000L)
                .build();


        member = Member.builder()
                .membership(membership)
                .memberId(memberId)
                .name(NAME)
                .id(ID)
                .nickname(NICKNAME)
                .build();

        Long roleId = 2L;
        role = Role.builder()
                .roleId(roleId)
                .roleType(RoleType.USER)
                .build();

        createResponse = MemberCreateResponse.fromEntity(member,role);

        updateResponse = MemberUpdateResponse.fromEntity(member);

    }

    @DisplayName("회원 등록시 사용자로부터 받은 데이터가 검증 조건에 맞지 않은 경우 요청에 실패하도록 하는 테스트")
    @Test
    @WithMockUser
    void signUpMember_invalidInputData() throws Exception {
        //given
        MemberCreateRequest request = new MemberCreateRequest();
        Mockito.when(memberService.createMember(any())).thenReturn(createResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/service/members/sign-up").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));

        //then
        perform.andDo(print()).andExpect(status().isBadRequest());
        verify(memberService, never()).createMember(any());

    }



    @DisplayName("회원 가입 테스트에 시 정규 표현식에 어긋나는 경우 요청 실패 테스트")
    @Test
    @WithMockUser
    void signUpMember_invalidRegex() throws Exception {
        //given
        MemberCreateRequest request = new MemberCreateRequest(
                ID,
                NICKNAME,
                NAME,
                GENDER,
                BIRTH,
                INVALID_PASSWORD,
                PHONENUMBER,
                EMAIL
        );

        Mockito.when(memberService.createMember(any())).thenReturn(createResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/service/members/sign-up").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));

        //then
        perform.andDo(print()).andExpect(status().isBadRequest());

        verify(memberService, never()).createMember(any());
    }

    @DisplayName("회원 가입 성공 테스트")
    @Test
    @WithMockUser
    void signUpMember() throws Exception {
        //given
        request = MemberCreateRequest.builder()
                .id(ID)
                .nickname(NICKNAME)
                .name(NAME)
                .gender(GENDER)
                .birthday(BIRTH)
                .password(VALID_PASSWORD)
                .phoneNumber(PHONENUMBER)
                .emailAddress(EMAIL)
                .build();

        request.toEntity(membership);

        MemberCreateResponse response = MemberCreateResponse.fromEntity(member, role);
        Mockito.when(memberService.createMember(any())).thenReturn(response);


        //when
        ResultActions perform = mockMvc.perform(post("/api/service/members/sign-up")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        String s = objectMapper.writeValueAsString(request);
        log.info("request string :{}", s);
        //then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.name", equalTo(member.getName())))
                .andExpect(jsonPath("$.data.nickname", equalTo(member.getNickname())))
                .andExpect(jsonPath("$.data.id", equalTo(member.getId())))
                .andExpect(jsonPath("$.data.role", equalTo((ROLE_USER))))
                .andExpect(jsonPath("$.data.grade", equalTo((Grade.WHITE.getName()))));

        verify(memberService, times(1)).createMember(any());

        //spring rest doc (api 자동화)
        perform.andDo(document(
                "sign-up-member-success",
                getDocumentRequest(),
                getDocumentsResponse(),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING)
                                .description("회원의 이름"),
                        fieldWithPath("id").type(JsonFieldType.STRING)
                                .description("회원 로그인 아이디"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                                .description("회원의 닉네임"),
                        fieldWithPath("emailAddress").type(JsonFieldType.STRING)
                                .description("회원의 이메일"),
                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                                .description("회원의 휴대전화 번호"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                                .description("회원 로그인에 사용할 비밀번호"),
                        fieldWithPath("birthday").type(JsonFieldType.STRING)
                                .description("회원의 생년월일"),
                        fieldWithPath("gender").type(JsonFieldType.STRING)
                                .description("회원의 성별")),
                responseFields(
                        fieldWithPath("success")
                                .type(JsonFieldType.BOOLEAN)
                                .description("동작 성공 여부"),
                        fieldWithPath("data.memberId")
                                .type(JsonFieldType.NUMBER)
                                .description("회원의 pk"),
                        fieldWithPath("data.name")
                                .type(JsonFieldType.STRING)
                                .description("회원의 이름"),
                        fieldWithPath("data.nickname")
                                .type(JsonFieldType.STRING)
                                .description("회원의 닉네임"),
                        fieldWithPath("data.id")
                                .type(JsonFieldType.STRING)
                                .description("회원 로그인 아이디"),
                        fieldWithPath("data.grade")
                                .type(JsonFieldType.STRING)
                                .description("회원의 등급"),
                        fieldWithPath("data.role")
                                .type(JsonFieldType.STRING)
                                .description("회원의 권한"),
                        fieldWithPath("status")
                                .type(JsonFieldType.NUMBER)
                                .description("HTTP 상태 코드"),
                        fieldWithPath("errorMessages")
                                .type(JsonFieldType.ARRAY)
                                .description("에러 메시지")
                                .optional())
                ));

    }

    @DisplayName("회원정보 수정 테스트 실패- @Valid 검증 조건을 충족하지 못한 경우")
    @ParameterizedTest(name = "{1}:{0}")
    @MethodSource(value = "updateMemberRequestData")
    @WithMockUser
    void testUpdateMember_ConflictData() throws Exception {

        //given
        Long memberId = 1L;
        MemberUpdateRequest request = ReflectionUtils.newInstance(
                MemberUpdateRequest.class,
                NICKNAME
        );

        //when
        ResultActions perform = mockMvc.perform(put("/api/service/members/{memberId}", memberId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andDo(print()).andExpect(status().isBadRequest());
        verify(memberService, never()).updateMember(anyLong(), any());
    }

    private static Stream<Arguments> updateMemberRequestData(){
        return Stream.of(
                Arguments.of("' '", "빈칸인 경우"),
                Arguments.of("'o", "2자리 미만인 경우"),
                Arguments.of("'mongmeo21", "숫자가 포함된 경우"),
                Arguments.of("'몽매오Ω≈ΩZ'", "특수문자가 포함된 경우"),
                Arguments.of("'hanadoolsetnetdasut'", "15자리 초과한 경우")
        );
    }

    @DisplayName("회원 수정 테스트 - 찾고자 하는 회원이 없는 경우")
    @Test
    @WithMockUser
    void testUpdateMember_notFoundMember() throws Exception {
        //given
        Long invalidMemberId = 1L;
        MemberUpdateRequest request = ReflectionUtils.newInstance(
                MemberUpdateRequest.class,
                NICKNAME
        );
        ArgumentCaptor<MemberUpdateRequest> requestArgumentCaptor = ArgumentCaptor.forClass(MemberUpdateRequest.class);
        Mockito.when(memberService.updateMember(eq(invalidMemberId),any()))
                .thenThrow(NotFoundMemberException.class);

        //when
        ResultActions perform = mockMvc.perform(put("/api/service/members/{memberId}", invalidMemberId).with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));


        //then
        perform.andDo(print()).andExpect(status().isNotFound());
        verify(memberService, times(1)).updateMember(anyLong(), requestArgumentCaptor.capture());

        Assertions.assertThat(requestArgumentCaptor.getValue().getNickname()).isEqualTo(request.getNickname());

    }

    @DisplayName("회원 수정 테스트 성공")
    @Test
    @WithMockUser
    void testUpdateMember_success() throws Exception {
        //given
        Long memberId = 1L;
        MemberUpdateRequest request = ReflectionUtils.newInstance(
                MemberUpdateRequest.class,
                NICKNAME
        );

        ArgumentCaptor<MemberUpdateRequest> requestArgumentCaptor = ArgumentCaptor.forClass(MemberUpdateRequest.class);
        Mockito.when(memberService.updateMember(eq(memberId),any())).thenReturn(updateResponse);


        //when
        ResultActions perform = mockMvc.perform(put("/api/service/members/{memberId}", memberId).with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));

        perform.andDo(print()).andExpect(status().isOk());
        verify(memberService, times(1)).updateMember(anyLong(),requestArgumentCaptor.capture());


    }

    @DisplayName("회원 삭제 테스트 - 성공")
    @Test
    @WithMockUser
    void testDeletedMember_success() throws Exception {
        //given
        String loginId = "test";

        Member deletedMember = Member.builder()
                .memberId(1L)
                .name("deleted 1")
                .isSoftDelete(true)
                .memberSoftDeletedAt(LocalDateTime.now())
                .build();

        deleteResponse = MemberSoftDeleteResponse.fromEntity(deletedMember);

        Mockito.when(queryMemberService.softDeleteLoginId(loginId))
                .thenReturn(deleteResponse);


        //when
        ResultActions perform = mockMvc.perform(delete("/api/service/members/delete/{id}", loginId)
                .with(csrf()).contentType(MediaType.APPLICATION_JSON));


        //then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.memberId",equalTo(deletedMember.getMemberId().intValue())))
                .andExpect(jsonPath("$.data.name",equalTo(deletedMember.getName())))
                .andExpect(jsonPath("$.data.softDelete", equalTo(deletedMember.isSoftDelete())))
                .andExpect(jsonPath("$.data.memberSoftDeletedAt",
                        equalTo(deletedMember.getMemberSoftDeletedAt().toString())));

        verify(queryMemberService, times(1)).softDeleteLoginId(loginId);

    }
}