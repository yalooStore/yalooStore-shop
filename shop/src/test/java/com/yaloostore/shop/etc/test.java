package com.yaloostore.shop.etc;

import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.exception.NotFoundMemberException;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TestRestController.class)
@AutoConfigureRestDocs
public class test {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryMemberService service;

    @Test
    void excludePathTest(){


        List<String> list = Arrays.asList("/api", "/dadasd/asdasdasd");

        boolean contains = list.contains("/api/dasdas");
        boolean contains_2 = list.contains("/dadasd/asdasdasd");


        assertThat(contains).isFalse();
        assertThat(contains_2).isTrue();

    }

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

    @Test
    @DisplayName("ModelAttribute 메서드 레벨에서 사용 테스트")
    public void parameterLevelModelAttributeTest() throws Exception {

        //given
        MethodParamLevelController methodParamLevelController = new MethodParamLevelController();

        MockMvc mockMvc1 = MockMvcBuilders.standaloneSetup(methodParamLevelController).build();

        //when
        ResultActions perform = mockMvc1.perform(MockMvcRequestBuilders.get("/modelAttribute/param/level/test")
                        .param("name", "test")
                        .param("age", "20")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        MvcResult result = mockMvc1.perform(MockMvcRequestBuilders.get("/param/level/test")
                        .param("name", "test")
                        .param("age", "20")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();

        //then
        perform.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Assertions.assertThat(response).isEqualTo("name: test, age: 20");
    }

    @Test
    @DisplayName("반환값 있는 ModelAttribute를 메서드 레벨에서 사용하는 테스트-쿠키 없음")
    public void methodLevelModelAttributeTest() throws Exception {

        //given
        MethodParamLevelController methodParamLevelController = new MethodParamLevelController();
        MockMvc mockMvc1 = MockMvcBuilders.standaloneSetup(methodParamLevelController).build();

        MvcResult result = mockMvc1.perform(MockMvcRequestBuilders.get("/modelAttribute")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        ResultActions perform = mockMvc1.perform(MockMvcRequestBuilders.get("/modelAttribute")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        perform.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String body = result.getResponse().getContentAsString();
        Assertions.assertThat(body).isEqualTo("쿠키 없어용~");
    }

}


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
class MethodParamLevel{
    private String name;
    private int age;
}


@RestController
@RequestMapping("/modelAttribute")
@RequiredArgsConstructor
class MethodParamLevelController{

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/param/level/test")
    public String paramLevelController(@ModelAttribute( "methodParamLevel") MethodParamLevel methodParamLevel){
        return "name: " + methodParamLevel.getName() + ", age: " + methodParamLevel.getAge();
    }

//    @ModelAttribute("modelAttribute_withReturn")
//    public String methodLevel_1(@CookieValue(name = "testCookie", required = false) Cookie testCookie){
//        if(Objects.isNull(testCookie)){
//            return "쿠키 없어용~";
//        }
//        return "쿠키 있어요~";
//    }
//    @ResponseStatus(HttpStatus.OK)
//    @ModelAttribute("modelAttribute_void")
//    public void methodLevel_2(Model model,
//                                  @CookieValue(name = "testCookie", required = false) Cookie testCookie){
//
//        if (Objects.nonNull(testCookie)){
//            model.addAttribute("hasCookie", "쿠키 있어용~~");
//        }
//
//    }


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
