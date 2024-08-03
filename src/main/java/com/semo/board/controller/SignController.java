package com.semo.board.controller;

import com.semo.board.SecurityUtils;
import com.semo.board.data.request.UserRequestDTO;
import com.semo.board.data.response.UserResponseDTO;
import com.semo.board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
@RequestMapping(value = "/sign")
public class SignController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(method = GET)
    public String sign() {
        return "signIn";
    }
    @RequestMapping(value = "/out", method = POST)
    @ResponseBody
    public ResponseEntity<?> signout(
            HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
            result.put("code", 0);
            result.put("message", "로그아웃 성공");
        } else{
            result.put("code", 1);
            result.put("message", "이미 로그아웃 상태");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @RequestMapping(value = "/in", method = POST)
    @ResponseBody
    public ResponseEntity<?> signin(HttpServletRequest request){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName(request.getParameter("userName"));
        userRequestDTO.setUserPassword(request.getParameter("userPassword"));
        Map<String, Object> result = userService.signIn(userRequestDTO);
        if ((int) result.get("code") == 0) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", userRequestDTO);
            session.setMaxInactiveInterval(5);
        } else {
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/up",method = POST)
    @ResponseBody
    public ResponseEntity<?> signup(
            HttpServletRequest request){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName(request.getParameter("userName"));
        userRequestDTO.setUserPassword(passwordEncoder.encode(request.getParameter("userPassword")));
        Map<String, Object> result = userService.checkId(userRequestDTO.getUserName());
        if (result.get("code").equals(0)) {
            try {
                userService.signUp(userRequestDTO);
                result.put("message", "아이디 생성 완료");
            } catch (Exception e) {
                result.put("code", -1);
                result.put("message", e.getMessage());
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/status", method = GET)
    @ResponseBody
    public ResponseEntity<?> status() {
        Map<String, Object> result = new HashMap<>();
        // Get the security context from the session
        if (SecurityUtils.isAuthenticated()) {
            String username = SecurityUtils.getCurrentUsername();
            result.put("message", "로그인 상태");
            result.put("username", username);
        } else {
            result.put("message", "로그아웃 상태");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
