package com.nft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nft.entity.GoodsTrade;
import com.nft.entity.ResultCode;
import com.nft.entity.User;
import com.nft.exception.MyException;
import com.nft.mapper.UserMapper;
import com.nft.service.EthBlockchainService;
import com.nft.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.util.LuhmUtil;
import com.nft.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    EthBlockchainService ethBlockchainService;

    @Override
    public User selectUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User selectUserById(Long userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    @Override
    public int insertUser(String username, String password, String payKey) throws Exception {
        User user = new User();
        user.setUserName(username);
        user.setUserPassword(StringUtil.md5(password + "aasd123viav9"));
        user.setRoles("user");
        user.setPerms("user:visit");
        user.setBalance(new BigDecimal(0));

//        生成一个该用户对应的区块链上的账户
//        JSONObject jsonObject = new JSONObject(ethBlockchainService.creatEthAddress(payKey));
//        user.setEthAddress((String) jsonObject.get("address"));
//        user.setKeySrc((String) jsonObject.get("fileName"));
        return userMapper.insert(user);
    }

    @Override
    public Double chargeETHB(Long userId, Double ethbAmount, String bankCard) {
        User user = userMapper.selectById(userId);
        user.setBalance(new BigDecimal(user.getBalance().doubleValue() + ethbAmount));
        // 验证银行卡号并储存
        if (!LuhmUtil.checkBankCard(bankCard))
            throw new MyException(ResultCode.WRONG_BANK_CARD_NUMBER);
        user.setBankCard(bankCard);

        int i = userMapper.updateById(user);
        if (i>0)
            return user.getBalance().doubleValue();
        return null;
    }

    @Override
    public Double getBalance(Long userId) {
        User user = userMapper.selectById(userId);
        return user.getBalance().doubleValue();
    }

}
