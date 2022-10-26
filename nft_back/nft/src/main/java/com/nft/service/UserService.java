package com.nft.service;

import com.nft.entity.GoodsTrade;
import com.nft.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
public interface UserService extends IService<User> {
    User selectUserByName(String username);

    User selectUserById(Long userId);

    int insertUser(String username, String password, String payKey) throws Exception;

    Double chargeETHB(Long userId, Double ethbAmount, String bankCard);

    Double getBalance(Long userId);
}
