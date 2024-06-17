package com.bluuminn.simplesns.fixture;

import com.bluuminn.simplesns.domain.PostEntity;
import com.bluuminn.simplesns.domain.UserEntity;

public class PostEntityFixture {
    public static PostEntity get(Integer postId, String username, Integer userId) {
        UserEntity user = UserEntity.builder()
                .id(userId)
                .username(username)
                .build();

        return PostEntity.of(postId, user);
    }
}
