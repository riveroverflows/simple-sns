package com.bluuminn.simplesns.fixture;

import com.bluuminn.simplesns.domain.PostEntity;
import com.bluuminn.simplesns.domain.UserEntity;

public class PostEntityFixture {
    public static PostEntity get(Integer postId, String username) {
        UserEntity user = UserEntity.builder()
                .id(1)
                .username(username)
                .build();

        return PostEntity.of(postId, user);
    }
}
