package com.yaloostore.shop;

import com.yaloostore.shop.member.controller.MemberLoginHistoryRestController;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.exception.NotFoundMemberException;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(TestRestController.class)
@AutoConfigureRestDocs
public class test {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryMemberService service;

    @Test
    @WithMockUser
    void test() throws Exception {


        String loginId = "test";

        when(service.findMemberByLoginId(loginId)).thenThrow(NotFoundMemberException.class);

        ResultActions perform = mockMvc.perform(get("/member/{loginId}", loginId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        perform.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.errorMessage", equalTo("not found member@")));



    }
    @Test
    @WithMockUser
    void test_two() throws Exception {


        String loginId = "test";

        when(service.findMemberByLoginId(loginId)).thenThrow(NotFoundMemberException.class);

        ResultActions perform = mockMvc.perform(get("/test/member/{loginId}", loginId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        perform.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())))
                .andExpect(jsonPath("$.errorMessage", equalTo("not found member@")));



    }
}

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
class ErrorResponse{
    private String errorCode;
    private String errorMessage;

    public static ErrorResponse of(HttpStatus errorCode, String errorMessage){
        return ErrorResponse.builder()
                .errorCode(errorCode.getReasonPhrase())
                .errorMessage(errorMessage)
                .build();
    }
}

@RestController
@RequiredArgsConstructor
class TestRestController{

    private final QueryMemberService queryMemberService;

    @GetMapping("/member/{loginId}")
    public ResponseEntity<MemberLoginResponse> getMemberByLoginId(@PathVariable(name = "loginId") String loginId){
        //1. 로그인 아이디를 사용해서 해당 회원을 찾아오는 로직이 있다고 예를 들어 봅시다.
        // 2. 그때 여기서 회원이 없어서 NotFoundMemberException이 발생했다고 생각해봅시다

        MemberLoginResponse member = queryMemberService.findMemberByLoginId(loginId);
        if(Objects.isNull(member)){
            throw new NotFoundMemberException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity exceptionHandlerMethod(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(
                        HttpStatus.NOT_FOUND, "not found member@"));

    }
}
@RestController
@RequiredArgsConstructor
class TestRestControllerTwo{

    private final QueryMemberService queryMemberService;

    @GetMapping("/test/member/{loginId}")
    public ResponseEntity<MemberLoginResponse> getMemberByLoginId(@PathVariable(name = "loginId") String loginId){
        //1. 로그인 아이디를 사용해서 해당 회원을 찾아오는 로직이 있다고 예를 들어 봅시다.
        // 2. 그때 여기서 회원이 없어서 NotFoundMemberException이 발생했다고 생각해봅시다

        MemberLoginResponse member = queryMemberService.findMemberByLoginId(loginId);
        if(Objects.isNull(member)){
            throw new NotFoundMemberException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

}
