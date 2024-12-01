package com.semo.board.service;

import com.semo.board.data.mapper.CommentMapper;
import com.semo.board.data.request.CommentRequestDTO;
import com.semo.board.data.response.CommentResponseDTO;
import com.semo.board.entity.CommentEntity;
import com.semo.board.entity.PostEntity;
import com.semo.board.entity.UserEntity;
import com.semo.board.repository.CommentRepository;
import com.semo.board.repository.PostRepository;
import com.semo.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    public Page<CommentResponseDTO> getComments(Pageable pageable, Long postId){
        Optional<PostEntity> post = postRepository.findById(postId);
        if(!pageable.getSort().isSorted()){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDate"));
        }
        if(post.isPresent()){
            PostEntity postEntity = post.get();
            Page<CommentEntity> entities = commentRepository.findAllByPostEntityAndDeletedFalse(pageable, postEntity);
            return CommentMapper.INSTANCE.toDTOPage(entities);
        } else{
            return null;
        }
    }

    public Map<String, Object> postComment(CommentRequestDTO comment, Long postId, String userName){
        Map<String, Object> result = new HashMap<>();
        CommentEntity entity = CommentMapper.INSTANCE.toEntity(comment);
        Optional<PostEntity> optionalPost = postRepository.findById(postId);
        UserEntity user = userRepository.findByUserName(userName);

        if(user == null){
            result.put("code", 2);
            result.put("message", "User is not present");
        } else if(!optionalPost.isPresent()){
            result.put("code", 1);
            result.put("message", "Post is not present");
        } else{
            PostEntity post = optionalPost.get();
            entity.setPostEntity(post);
            entity.setUserEntity(user);
            commentRepository.save(entity);
            result.put("code", 0);
            result.put("message", "success");
        }
        return result;
    }
    public Map<String, Object> deleteComment(){
        return null;
    }
    public Map<String, Object> updateComment(){
        return null;
    }

}
