package com.semo.board.repository;

import com.semo.board.entity.CommentEntity;
import com.semo.board.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllByPostEntityAndDeletedFalse(Pageable pageable, PostEntity postEntity);
}
