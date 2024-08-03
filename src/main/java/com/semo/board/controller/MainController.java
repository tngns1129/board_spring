package com.semo.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<?> home() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("message", "Hi I'm Semo Board");

        //return "index.html";
        return ResponseEntity.ok(result);
    }

}
