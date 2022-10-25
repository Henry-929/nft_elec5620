package com.nft.controller;


import com.nft.entity.GoodsTrade;
import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import com.nft.entity.User;
import com.nft.service.ShoppingCarService;
import com.nft.service.UserService;
import com.nft.util.ParamUtil;
import com.nft.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@RestController
@RequestMapping("/shopping-car")
public class ShoppingCarController {

    @Autowired
    ShoppingCarService shoppingCarService;
    @Autowired
    UserService userService;

    /**
     * 购买单个nft
     */
    @PostMapping( "/buyGoodsById")
    public Result buyGoodsById(@RequestBody Map<String, Object> map){
        Long userId = ParamUtil.tradeToLong(map.get("userId"));        // userId
        Long goodsId = ParamUtil.tradeToLong(map.get("goodsId"));        // 商品ID
        String payKey = map.get("payKey").toString();     // 支付密码

        if (userId == null || goodsId == null || payKey == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        GoodsTrade goodsTrade = shoppingCarService.buyGoodsById(userId,goodsId,payKey);

        if (goodsTrade == null) {
            return new Result(ResultCode.SERVER_ERROR);

        }
        return new Result(ResultCode.SUCCESS,goodsTrade);
    }

    /**
     * 购买多个nft
     */
    @PostMapping( "/buyManyGoodsById")
    public Result buyManyGoodsById(@RequestBody Map<String, Object> map){
        Long userId = ParamUtil.tradeToLong(map.get("userId"));        // userId
        List<Long> goodsIdList = (List<Long>) map.get("goodsIdList");        // 多个nft goodID，是个list
        double totalPrice = ParamUtil.tradeToDouble(map.get("totalPrice"));            //多个nft总价
        String payKey = map.get("payKey").toString();     // 支付密码

        if (userId == null || goodsIdList.size() <= 0 || payKey == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        User user = userService.selectUserById(userId);
        if (totalPrice > user.getBalance().doubleValue()){
            return new Result(ResultCode.INSUFFICIENT_BALANCE);
        }

        for (Long goodsId : goodsIdList ) {
            GoodsTrade goodsTrade = shoppingCarService.buyGoodsById(userId, goodsId, payKey);

            if (goodsTrade == null) {
                return new Result(ResultCode.SERVER_ERROR);

            }
        }

        return new Result(ResultCode.SUCCESS);
    }
}
