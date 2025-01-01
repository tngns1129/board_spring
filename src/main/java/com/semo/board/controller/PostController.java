package com.semo.board.controller;

import com.semo.board.SecurityUtils;
import com.semo.board.data.mapper.PostMapper;
import com.semo.board.data.request.PostRequestDTO;
import com.semo.board.service.BlockService;
import com.semo.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Block;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping(value = "/post")
@RequiredArgsConstructor
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    @Autowired
    private final PostService postService;
    @Autowired
    private final BlockService blockService;

    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity<?> getPosts(
            Pageable pageable
    ){
        try{
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success search");
            result.put("data", postService.getAllPostNotBlocked(userName,pageable));
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(method = POST)
    @ResponseBody
    public ResponseEntity<?> writePost(
            @RequestBody PostRequestDTO requestDTO){
        try {
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = postService.writePost(userName, requestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(method = DELETE)
    @ResponseBody
    public ResponseEntity<?> deletePost(
            @RequestParam(required = true, value = "postId") Long postId
    ){
        try {
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = postService.deletePost(postId, userName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
    @RequestMapping(method = PUT)
    @ResponseBody
    public ResponseEntity<?> updatePost(
            @RequestBody PostRequestDTO requestDTO,
            @RequestParam(required = true, value = "postId") Long postId
    ){
        try {
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = postService.updatePost(postId, userName, requestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(value = "/block", method = POST)
    @ResponseBody
    public ResponseEntity<?> blockPost(
            @RequestParam(required = true, value = "postId") Long postId
    ){
        try {
            String userName = SecurityUtils.getCurrentUsername();
            blockService.blockPost(userName, postId);
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
}
