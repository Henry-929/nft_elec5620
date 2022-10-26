package com.nft.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nft.entity.*;
import com.nft.entity.vo.SimpleGoods;
import com.nft.exception.MyException;
import com.nft.mapper.ShoppingCarMapper;
import com.nft.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class ShoppingCarServiceImpl extends ServiceImpl<ShoppingCarMapper, ShoppingCar> implements ShoppingCarService {

    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    ArtService artService;
    @Autowired
    GoodsTradeService goodsTradeService;
    @Autowired
    ShoppingCarMapper shoppingCarMapper;

    @Override
    @Transactional
    public GoodsTrade buyGoodsById(Long userId, Long goodsId, String payKey) {
        //获取购买用户
        User user = userService.selectUserById(userId);
        if (!StringUtil.md5(payKey+"aasd123viav9").equals(user.getUserPassword()) ){
            throw new MyException(ResultCode.INCORRECT_CREDENTAIL_ERROR);
        }

        // 获取商品
        Goods goods = goodsService.getById(goodsId);
        // 如果查询不到该商品，则
        if (goods == null) {
            return null;
        }
        //查看账户余额
        double checkBalance = user.getBalance().doubleValue() - goods.getResalePrice().doubleValue();
        if (checkBalance<0){
            throw new MyException(ResultCode.INSUFFICIENT_BALANCE);
        }
        // 获取艺术品记录
        Art art = artService.getById(goods.getArtId());
        if (art == null) {
            return null;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        // 如果时所有权变更，则创造所有权变更交易
        GoodsTrade goodsTrade = new GoodsTrade();
        goodsTrade.setGoodsId(goodsId);
        goodsTrade.setSellerId(goods.getGoodsSellerId());
        goodsTrade.setBuyerId(userId);
        goodsTrade.setCoin(goods.getResalePrice());
        if (!goodsTradeService.save(goodsTrade)) {
            return null;
        }
        // 将交易记录放回
        hashMap.put("trade", goodsTrade);

        // 修改商品的状态,删除售卖商品
        if (!goodsService.removeById(goodsId)){
            return null;
        }

        // 更改nft在的所有权
        UpdateWrapper<Art> wrapper = new UpdateWrapper<Art>()
                .set("owner_id", userId)
                .eq("art_id", art.getArtId());
        artService.update(wrapper);

        //更新用户账户余额
        UpdateWrapper<User> wrapper2 = new UpdateWrapper<User>()
                .set("balance", checkBalance)
                .eq("user_id", user.getUserId());
        userService.update(wrapper2);

        return goodsTrade;
    }

    @Override
    public ArrayList<SimpleGoods> getShoppingCar(Long userId) {
        return shoppingCarMapper.getShoppingCar(userId);
    }

    @Override
    public Boolean setShoppingCar(Long userId, Long goodId) {
        Goods good = goodsService.selectGoodsById(goodId);
        if (good.getGoodsSellerId() == userId)
            throw new MyException(ResultCode.NOT_ADD_TO_CART);

        ShoppingCar shoppingCar = new ShoppingCar();
        shoppingCar.setUserId(userId);
        shoppingCar.setGoodsId(goodId);
        return shoppingCarMapper.insert(shoppingCar) > 0;
    }

    @Override
    public Boolean deleteShoppingCar(Long userId, Long goodId) {
        UpdateWrapper<ShoppingCar> wrapper = new UpdateWrapper<ShoppingCar>()
                .eq("user_id", userId)
                .eq("goods_id", goodId);
        return shoppingCarMapper.delete(wrapper) > 0;
    }
}
