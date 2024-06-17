package com.bluuminn.simplesns.fixture;

import com.bluuminn.simplesns.domain.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String username, String password, Integer userId) {
        return UserEntity.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();
    }
}
