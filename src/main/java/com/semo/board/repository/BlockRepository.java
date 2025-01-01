package com.semo.board.repository;

import com.semo.board.entity.BlockEntity;
import com.semo.board.entity.PostEntity;
import com.semo.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    List<BlockEntity> findByUser(UserEntity user);
    Optional<BlockEntity> findByUserAndPost(UserEntity user, PostEntity post);
}