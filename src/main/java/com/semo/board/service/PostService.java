package com.semo.board.service;

import com.semo.board.data.mapper.PostMapper;
import com.semo.board.data.request.PostRequestDTO;
import com.semo.board.data.response.PostResponseDTO;
import com.semo.board.entity.PostEntity;
import com.semo.board.entity.UserEntity;
import com.semo.board.repository.PostRepository;
import com.semo.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostRepository postRepository;

    public Page<PostResponseDTO> getAllPost(Pageable pageable){
        Page<PostEntity> entityPage = postRepository.findAllByDeletedFalse(pageable);
        return PostMapper.INSTANCE.toDTOPage(entityPage);

    }
    public Map<String, Object> writePost(String username, PostRequestDTO postRequestDTO){
        HashMap<String, Object> result = new HashMap<>();
        try{
            PostEntity entity = PostMapper.INSTANCE.toEntity(postRequestDTO);
            if(username == null){
                //todo: 비로그인 사용자
            }
            UserEntity user = userRepository.findByUserName(username);
            if(user == null){
                result.put("code", 1);
                result.put("message", "There is not user");
            }else{
                result.put("code", 0);
                result.put("message", "Write Post Success");
                entity.setUserEntity(user);
                postRepository.save(entity);
            }

        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 2);
            result.put("message", "An error occurred while updating the post");
        }
        return result;
    }

    public HashMap<String, Object> deletePost(Long postId, String userName){
        HashMap<String, Object> result = new HashMap<>();
        try{
            Optional<PostEntity> optionalEntity = postRepository.findById(postId);
            if (optionalEntity.isPresent()) {
                PostEntity postEntity = optionalEntity.get();
                // Use postEntity
                if(postEntity.getUserEntity().equals(userRepository.findByUserName(userName))){
                    postEntity.setDeleted(true);
                    result.put("code", 0);
                    result.put("message", "Delete Post Success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this post and cannot delete it");
                }
            } else {
                // Handle the case where the entity is not found
                result.put("code", 1);
                result.put("message", "There is not post");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", -1);
            result.put("message", "An error occurred while updating the post");
        }
        return result;
    }
    public HashMap<String, Object> updatePost(Long postId, String userName, PostRequestDTO postRequestDTO){
        HashMap<String, Object> result = new HashMap<>();
        try{
            Optional<PostEntity> optionalEntity = postRepository.findById(postId);
            if (optionalEntity.isPresent()) {
                PostEntity postEntity = optionalEntity.get();
                // Use postEntity
                if(postEntity.getUserEntity().equals(userRepository.findByUserName(userName))){
                    PostMapper.INSTANCE.updateEntityFromDto(postRequestDTO, postEntity);
                    postRepository.save(postEntity);
                    result.put("code", 0);
                    result.put("message", "Update Success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this post and cannot update it");
                }
            } else {
                // Handle the case where the entity is not found
                result.put("code", 1);
                result.put("message", "There is not post");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 3);
            result.put("message", "An error occurred while updating the post");
        }
        return result;
    }
}
