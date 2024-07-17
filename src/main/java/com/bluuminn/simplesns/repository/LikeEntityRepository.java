package com.bluuminn.simplesns.repository;

import com.bluuminn.simplesns.domain.LikeEntity;
import com.bluuminn.simplesns.domain.PostEntity;
import com.bluuminn.simplesns.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);

    @Query("select count(*) from LikeEntity l where l.post = :post")
    Integer countByPost(@Param("post") PostEntity post);

    // Integer countByPost(PostEntity post); 이거랑 동일하게 동작하는듯 ..
}
