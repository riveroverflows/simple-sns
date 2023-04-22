package com.bluuminn.simplesns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserLoginResponse {
    private String token;
}
