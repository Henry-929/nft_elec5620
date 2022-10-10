package com.nft.controller;


import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import com.nft.entity.User;
import com.nft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> map){
        String username = (String) map.get("username");     //用户名
        String password = (String) map.get("password");     //登录密码
        String payKey = (String) map.get("payKey");         //支付密码

        if (username == null || password == null || payKey == null)
            return new Result(ResultCode.PARAMETER_NULL_ERROR);

        User searchUser = userService.selectUserByName(username);
        if (null != searchUser)
            return new Result(ResultCode.REPEAT_USER_ACCOUNTERROR);

        userService.insertUser(username,password,payKey);

        return new Result(ResultCode.SUCCESS);
    }

}
