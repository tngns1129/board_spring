package com.semo.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class BlockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    private LocalDateTime blockedAt = LocalDateTime.now();

    public BlockEntity() {}

    public BlockEntity(UserEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockEntity)) return false;
        BlockEntity block = (BlockEntity) o;
        return Objects.equals(user, block.user) &&
                Objects.equals(post, block.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }
}
