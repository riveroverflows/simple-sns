package com.bluuminn.simplesns.controller;

import com.bluuminn.simplesns.controller.request.UserJoinRequest;
import com.bluuminn.simplesns.controller.request.UserLoginRequest;
import com.bluuminn.simplesns.controller.response.Response;
import com.bluuminn.simplesns.controller.response.UserJoinResponse;
import com.bluuminn.simplesns.controller.response.UserLoginResponse;
import com.bluuminn.simplesns.model.User;
import com.bluuminn.simplesns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
