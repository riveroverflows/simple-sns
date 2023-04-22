package com.bluuminn.simplesns.controller.response;

import com.bluuminn.simplesns.model.User;
import com.bluuminn.simplesns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private Integer id;
    private String username;
    private UserRole role;

    public static UserJoinResponse fromUser(User user) {
        return UserJoinResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
