package com.semo.board.controller;

import com.semo.board.SecurityUtils;
import com.semo.board.data.request.CommentRequestDTO;
import com.semo.board.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity<?> getComment(
            Pageable pageable,
            @RequestParam(required = true, value = "postId") Long postId
    ){
        try{
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success get comments");
            result.put("data", commentService.getComments(pageable, postId));
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
    public ResponseEntity<?> postComment(
            @RequestBody CommentRequestDTO requestDTO,
            @RequestParam(required = true, value = "postId") Long postId

            ){
        try{
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = commentService.postComment(requestDTO, postId, userName);
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
    @RequestMapping(method = PUT)
    @ResponseBody
    public ResponseEntity<?> updateComment(
            @RequestBody CommentRequestDTO requestDTO,
            @RequestParam(required = true) Long commentId
    ){
        try{
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = commentService.updateComment(commentId,userName,requestDTO);
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
    @RequestMapping(method = DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteComment(
            @RequestParam(required = true) Long commentId
    ){
        try{
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = commentService.deleteComment(commentId, userName);
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
}
