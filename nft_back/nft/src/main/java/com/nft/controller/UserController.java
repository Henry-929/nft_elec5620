package com.nft.controller;


import com.nft.entity.GoodsTrade;
import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import com.nft.entity.User;
import com.nft.service.GoodsTradeService;
import com.nft.service.UserService;
import com.nft.shiro.JwtToken;
import com.nft.util.JwtUtil;
import com.nft.util.ParamUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    @Autowired
    GoodsTradeService goodsTradeService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> mymap){
        String username = (String) mymap.get("username");     //用户名
        String password = (String) mymap.get("password");     //登录密码
        if (username == null || password == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        String token = JwtUtil.createJWT(username, "back", "user", 1000 * 60 * 30);
        JwtToken jwtToken = new JwtToken(token, password);
        HashMap<String, Object> map = new HashMap<>();
        try {
            subject.login(jwtToken);

            User userFromDB = (User) subject.getPrincipal();
            System.out.println("userFromDB is "+userFromDB.toString());
            map.put("token", token);
            map.put("user", userFromDB);
            return new Result(ResultCode.SUCCESS,map);
        } catch (IncorrectCredentialsException ice) {
            return new Result(ResultCode.INCORRECT_CREDENTAIL_ERROR);
        } catch (UnknownAccountException uae) {
            return new Result(ResultCode.UNKONWN_ACCOUNT_ERROR);
        }
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> map) throws Exception {
        String username = (String) map.get("username");     //用户名
        String password = (String) map.get("password");     //登录密码
        String payKey = (String) map.get("payKey");         //支付密码

        if (username == null || password == null || payKey == null)
            return new Result(ResultCode.PARAMETER_NULL_ERROR);

        User searchUser = userService.selectUserByName(username);
        if (null != searchUser)
            return new Result(ResultCode.REPEAT_USER_ACCOUNTERROR);

        int i = userService.insertUser(username, password, payKey);
        return i>0 ? new Result(ResultCode.SUCCESS) : new Result(ResultCode.FAIL);
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    @RequiresPermissions( value = {"user:visit","admin:manage"}, logical = Logical.OR)
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 充值
     */
    @PostMapping("/charge")
    public Result chargeMTB(@RequestBody Map<String,Object> map){
        Long userId = ParamUtil.tradeToLong(map.get("userId"));                           // userId
        Double ETHBAmount = ParamUtil.tradeToDouble(map.get("ETHBAmount"));              // 充值金额
        String bankCard = ParamUtil.tradeToString(map.get("bankCard"));                   // 银行卡号

        if (userId == null || ETHBAmount == null || bankCard == null){
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }
        Double balance = userService.chargeETHB(userId,ETHBAmount,bankCard);

        if (balance == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,balance);
    }

    /**
     * 查询余额
     */
    @PostMapping("/getBalance")
    public Result getBalance(@RequestBody Map<String, Object> map){
        Long userId = ParamUtil.tradeToLong(map.get("userId"));        // userId

        if (userId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        Double balance = userService.getBalance(userId);
        if (balance == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,balance);
    }

    /**
     * 获取本人购买记录
     */
    @PostMapping("/getBuyOrder")
    public Result getBuyOrder(@RequestBody Map<String, Object> map) {
        Long userId = ParamUtil.tradeToLong(map.get("userId"));        // userId

        if (userId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        List<GoodsTrade> temList = goodsTradeService.getBuyOrder(userId);
        if (temList.size() == 0) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }
        return new Result(ResultCode.SUCCESS,temList);
    }

    /**
     * 获取本人售出记录
     */
    @PostMapping("/getSaleOrder")
    public Result getSaleOrder(@RequestBody Map<String, Object> map) {
        Long userId = ParamUtil.tradeToLong(map.get("userId"));        // userId

        if (userId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        List<GoodsTrade> temList = goodsTradeService.getSaleOrder(userId);
        if (temList.size() == 0) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }
        return new Result(ResultCode.SUCCESS,temList);
    }

    /**
     * 测试ab
     */
    @GetMapping("/ab")
    @RequiresPermissions( value = {"user:visit","admin:manage"}, logical = Logical.OR)
    public Result ab(){
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 测试cd
     */
    @GetMapping("/cd")
    @RequiresPermissions("admin:manage")
    public Result cd(){
        return new Result(ResultCode.SUCCESS);
    }
}
