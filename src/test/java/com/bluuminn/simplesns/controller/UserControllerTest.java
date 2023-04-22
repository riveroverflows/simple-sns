package com.bluuminn.simplesns.controller;

import com.bluuminn.simplesns.controller.request.UserJoinRequest;
import com.bluuminn.simplesns.controller.request.UserLoginRequest;
import com.bluuminn.simplesns.exception.ErrorCode;
import com.bluuminn.simplesns.exception.SnsApplicationException;
import com.bluuminn.simplesns.model.User;
import com.bluuminn.simplesns.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("회원가입")
    @Test
    void join() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입시 이미 회원가입된 username으로 회원가입을 하는 경우 에러 반환")
    @Test
    void duplicate_username() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USERNAME));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @DisplayName("로그인")
    @Test
    void login() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.login(username, password)).thenReturn("test_token");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 시 회원가입이 안된 username을 입력할 경우 에러 반환")
    @Test
    void no_username() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.login(username, password)).thenThrow(new SnsApplicationException(ErrorCode.USER_NOT_FOUNT));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("로그인 시 틀린 패스워드를 입력할 경우 에러 반환")
    @Test
    void wrong_password() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.login(username, password)).thenThrow(new SnsApplicationException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
