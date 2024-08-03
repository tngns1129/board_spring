package com.semo.board.repository;

import com.semo.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String id);
    Set<UserEntity> findByUserNameIn(Set<String> usernames);

}