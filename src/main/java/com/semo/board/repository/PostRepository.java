package com.semo.board.repository;

import com.semo.board.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByDeletedFalse(Pageable pageable);
    Page<PostEntity> findAllByDeletedFalseAndIdNotIn(Pageable pageable, List<Long> blockedPostIds);
}
