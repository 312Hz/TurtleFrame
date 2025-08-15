package com.ll.service;


import com.ll.pojo.dto.UserLoginDTO;
import com.ll.pojo.entity.User;

public interface UserService {

    /**
     * 员工登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);
}
