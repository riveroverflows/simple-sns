package com.bluuminn.simplesns.service;

import com.bluuminn.simplesns.domain.PostEntity;
import com.bluuminn.simplesns.domain.UserEntity;
import com.bluuminn.simplesns.exception.ErrorCode;
import com.bluuminn.simplesns.exception.SnsApplicationException;
import com.bluuminn.simplesns.fixture.PostEntityFixture;
import com.bluuminn.simplesns.fixture.UserEntityFixture;
import com.bluuminn.simplesns.repository.PostEntityRepository;
import com.bluuminn.simplesns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.when;


@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
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
    void no_user_when_create_post() throws Exception {
        String title = "title";
        String body = "body";
        String username = "username";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.create(title, body, username));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @DisplayName("포스트 수정이 성공한 경우")
    @Test
    void modify_posts_success() throws Exception {
        String title = "title";
        String body = "body";
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(postId, username, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        Assertions.assertDoesNotThrow(() -> postService.modify(title, body, username, postId));
    }

    @DisplayName("포스트 수정 시 포스트가 존재하지 않는 경우")
    @Test
    void not_exist_when_modify_post() throws Exception {
        String title = "title";
        String body = "body";
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(postId, username, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException exception = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, username, postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("포스트 수정 시 권한이 없는 경우")
    @Test
    void invalid_user_when_modify_post() throws Exception {
        String title = "title";
        String body = "body";
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(postId, username, 1);
        UserEntity writer = UserEntityFixture.get("writer", "password", 2);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException exception = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, username, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }

    @DisplayName("포스트 삭제가 성공한 경우")
    @Test
    void delete_posts_success() throws Exception {
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(postId, username, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        Assertions.assertDoesNotThrow(() -> postService.delete(postId, username));
    }

    @DisplayName("포스트 삭제 시 포스트가 존재하지 않는 경우")
    @Test
    void not_exist_when_delete_post() throws Exception {
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(postId, username, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException exception = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete(postId, username));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("포스트 삭제 시 권한이 없는 경우")
    @Test
    void invalid_user_when_delete_post() throws Exception {
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(postId, username, 1);
        UserEntity writer = UserEntityFixture.get("writer", "password", 2);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException exception = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete(postId, username));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }

    @DisplayName("포스트 목록 조회가 성공한 경우")
    @Test
    void get_posts_success() throws Exception {
        Pageable pageable = mock(Pageable.class);
        when(postEntityRepository.findAll(pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(() -> postService.list(pageable));
    }

    @DisplayName("내 포스트 목록 조회가 성공한 경우")
    @Test
    void get_my_posts_success() throws Exception {
        Pageable pageable = mock(Pageable.class);
        when(postEntityRepository.findAllByUser(any(), pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(() -> postService.my("", pageable));
    }
}
