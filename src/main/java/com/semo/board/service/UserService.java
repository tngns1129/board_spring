package com.semo.board.service;

import com.semo.board.data.mapper.UserMapper;
import com.semo.board.data.request.UserRequestDTO;
import com.semo.board.entity.UserEntity;
import com.semo.board.entity.UserType;
import com.semo.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public HashMap<String, Object> signIn(UserRequestDTO userRequestDTO){
        HashMap<String, Object> result = new HashMap<>();
        UserEntity entity = userRepository.findByUserName(userRequestDTO.getUserName());
        if(entity == null){
            result.put("code", 1);
            result.put("message", "존재하지 않는 아이디");
            return result;
        }else if(!entity.getUserPassword().equals(userRequestDTO.getUserPassword())){
            result.put("code", 2);
            result.put("message", "패스워드 불일치");
        } else{
            result.put("code", 0);
            result.put("message", "로그인 성공");
        }

        return result;
    }

    public void signUp(UserRequestDTO userRequestDTO){
        UserEntity entity = UserMapper.INSTANCE.toEntity(userRequestDTO);
        entity.setUserType(UserType.USER);
        userRepository.save(entity);
    }

    public HashMap<String, Object> checkId(String id) {
        HashMap<String, Object> result = new HashMap<>();
        String regex = "^[a-zA-Z0-9]+$";
        UserEntity entity = userRepository.findByUserName(id);
        // 아이디가 null이거나 빈 문자열확인
        if (id == null || id.isEmpty()) {
            result.put("code", 1);
            result.put("message", "null 값");
        } else if (!id.matches(regex)) {// 공백, 특수문자 확인
            result.put("code", 2);
            result.put("message", "ID에 공백 또는 특수문자");
        } else if (entity != null) {// 아이디 중복 확인
            result.put("code", 3);
            result.put("message", "중복된 ID");
        } else{
            result.put("code", 0);
        }
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByUserName(username);
        if (entity == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return User.builder().username(entity.getUserName()).password(entity.getUserPassword()).roles(entity.getUserType().name()).build();
    }
}