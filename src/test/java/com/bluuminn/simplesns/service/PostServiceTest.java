package com.bluuminn.simplesns.service;

import com.bluuminn.simplesns.domain.PostEntity;
import com.bluuminn.simplesns.domain.UserEntity;
import com.bluuminn.simplesns.exception.ErrorCode;
import com.bluuminn.simplesns.exception.SnsApplicationException;
import com.bluuminn.simplesns.repository.PostEntityRepository;
import com.bluuminn.simplesns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.when;


@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostEntityRepository postEntityRepository;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @DisplayName("포스트 작성이 성공한 경우")
    @Test
    void create_posts_success() throws Exception {
        String title = "title";
        String body = "body";
        String username = "username";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        Assertions.assertDoesNotThrow(() -> postService.create(title, body, username));
    }

    @DisplayName("포스트 작성 시 요청한 유저가 존재하지 않는 경우")
    @Test
    void create_posts_when_no_user() throws Exception {
        String title = "title";
        String body = "body";
        String username = "username";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.create(title, body, username));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }
}