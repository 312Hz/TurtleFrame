package com.ll.controller;

import com.ll.constant.JwtClaimsConstant;
import com.ll.exception.AccountNotFoundException;
import com.ll.pojo.dto.UserLoginDTO;
import com.ll.pojo.entity.User;
import com.ll.pojo.vo.UserLoginVO;
import com.ll.properties.JwtProperties;
import com.ll.result.Result;
import com.ll.service.UserService;
import com.ll.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(UserLoginDTO userLoginDTO){
        log.info("用户登录：{}",userLoginDTO);
        User user = userService.login(userLoginDTO);
        //登录成功生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .name(user.getName())
                .userName(user.getUsername())
                .password(user.getPassword())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

}
