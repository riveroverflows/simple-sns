package com.bluuminn.simplesns.service;

import com.bluuminn.simplesns.domain.PostEntity;
import com.bluuminn.simplesns.domain.UserEntity;
import com.bluuminn.simplesns.exception.ErrorCode;
import com.bluuminn.simplesns.exception.SnsApplicationException;
import com.bluuminn.simplesns.repository.PostEntityRepository;
import com.bluuminn.simplesns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String username) {
        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));
        postEntityRepository.save(PostEntity.of(title, body, user));
    }

    @Transactional
    public void modify(String title, String body, String username, Integer postId) {
        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));

        // post exists

        // post permission
    }
}
