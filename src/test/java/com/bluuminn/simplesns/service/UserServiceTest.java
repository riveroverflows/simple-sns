package com.bluuminn.simplesns.service;

import com.bluuminn.simplesns.domain.UserEntity;
import com.bluuminn.simplesns.repository.UserEntityRepository;
import com.bluuminn.simplesns.exception.ErrorCode;
import com.bluuminn.simplesns.exception.SnsApplicationException;
import com.bluuminn.simplesns.fixture.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @DisplayName("회원가입이 정상적으로 동작하는 경우")
    @Test
    void join_pass() throws Exception {
        // given
        String username = "username";
        String password = "password";

        // when
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(username, password));

        // then
        assertDoesNotThrow(() -> userService.join(username, password));
    }

    @DisplayName("회원가입 시 username으로 회원가입한 유저가 이미 있는 경우")
    @Test
    void duplicate_username() throws Exception {
        // given
        String username = "username";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);

        // when
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        // then
        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> userService.join(username, password));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.DUPLICATED_USERNAME);
    }

    @DisplayName("로그인이 정상적으로 동작하는 경우")
    @Test
    void login_pass() throws Exception {
        // given
        String username = "username";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);

        // when
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        // then
        assertDoesNotThrow(() -> userService.login(username, password));
    }

    @DisplayName("로그인 시 username으로 회원가입한 유저가 없는 경우")
    @Test
    void no_username() throws Exception {
        // given
        String username = "username";
        String password = "password";

        // when
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());

        // then
        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> userService.login(username, password));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
    }

    @DisplayName("로그인 시 패스워드가 틀린 경우")
    @Test
    void wrong_password() throws Exception {
        // given
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";
        UserEntity fixture = UserEntityFixture.get(username, password);

        // when
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

        // then
        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> userService.login(username, wrongPassword));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD);
    }
}