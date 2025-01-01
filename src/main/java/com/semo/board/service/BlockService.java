package com.semo.board.service;

import com.semo.board.entity.BlockEntity;
import com.semo.board.entity.PostEntity;
import com.semo.board.entity.UserEntity;
import com.semo.board.repository.BlockRepository;
import com.semo.board.repository.PostRepository;
import com.semo.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockService {

    @Autowired
    private final BlockRepository blockRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostRepository postRepository;

    // 게시물 차단
    public BlockEntity blockPost(String userName, Long postId) {
        UserEntity user = userRepository.findByUserName(userName);
        Optional<PostEntity> post = postRepository.findById(postId);
        PostEntity postEntity = null;
        if(post.isPresent()){
            postEntity = post.get();
        }
        if (blockRepository.findByUserAndPost(user, postEntity).isPresent()) {
            throw new IllegalStateException("Post is already blocked by the user");
        }
        BlockEntity block = new BlockEntity(user, postEntity);
        return blockRepository.save(block);
    }

    // 게시물 차단 해제
    public void unblockPost(String userName, Long postId) {
        UserEntity user = userRepository.findByUserName(userName);
        Optional<PostEntity> post = postRepository.findById(postId);
        PostEntity postEntity = null;
        if(post.isPresent()){
            postEntity = post.get();
        }
        Optional<BlockEntity> block = blockRepository.findByUserAndPost(user, postEntity);
        block.ifPresent(blockRepository::delete);
    }

    // 특정 사용자가 차단한 게시물 조회
    public List<PostEntity> getBlockedPostsByUser(UserEntity user) {
        return blockRepository.findByUser(user).stream()
                .map(BlockEntity::getPost)
                .collect(Collectors.toList());
    }

    // 게시물이 차단되었는지 확인
    public boolean isPostBlockedByUser(UserEntity user, PostEntity post) {
        return blockRepository.findByUserAndPost(user, post).isPresent();
    }
}
