package com.ll.service.impl;

import com.ll.constant.MessageConstant;
import com.ll.constant.StatusConstant;
import com.ll.exception.AccountLockedException;
import com.ll.exception.AccountNotFoundException;
import com.ll.exception.PasswordErrorException;
import com.ll.mapper.UserMapper;
import com.ll.pojo.dto.UserLoginDTO;
import com.ll.pojo.entity.User;
import com.ll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 员工登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //根据用户查询用户
        User user = userMapper.getUsername(username);
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        if(!password.equals(user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if(user.getStatus().equals(StatusConstant.DISABLE)){
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return user;
    }
}
