package com.bluuminn.simplesns.fixture;

import com.bluuminn.simplesns.domain.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String username, String password) {
        return UserEntity.builder()
                .id(1)
                .username(username)
                .password(password)
                .build();
    }
}
