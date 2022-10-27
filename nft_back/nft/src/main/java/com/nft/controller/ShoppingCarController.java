package com.nft.controller;


import com.nft.entity.GoodsTrade;
import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import com.nft.entity.User;
import com.nft.entity.vo.SimpleGoods;
import com.nft.service.ShoppingCarService;
import com.nft.service.UserService;
import com.nft.util.ParamUtil;
import com.nft.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        GoodsTrade goodsTrade = shoppingCarService.buyGoodsById(goodsId, userId, payKey);

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
        List<Integer> goodsIdList = (List<Integer>) map.get("goodsIdList");        // 多个nft goodID，是个list
        double totalPrice = ParamUtil.tradeToDouble(map.get("totalPrice"));            //多个nft总价
        String payKey = map.get("payKey").toString();     // 支付密码

        if (userId == null || goodsIdList.size() <= 0 || payKey == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        User user = userService.selectUserById(userId);
        if (totalPrice > user.getBalance().doubleValue()){
            return new Result(ResultCode.INSUFFICIENT_BALANCE);
        }

        for (int i=0;i<goodsIdList.size();i++) {
            Long goodsId = Long.valueOf(goodsIdList.get(i));
            GoodsTrade goodsTrade = shoppingCarService.buyGoodsById(goodsId, userId, payKey);

            if (goodsTrade == null) {
                return new Result(ResultCode.SERVER_ERROR);
            }
        }

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取本人的购物车
     */
    @PostMapping("/getShoppingCar")
    public Result getShoppingCar(@RequestBody Map<String, Object> map) {
        Long userId = ParamUtil.tradeToLong(map.get("userId"));
        if (userId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<SimpleGoods> arts = shoppingCarService.getShoppingCar(userId);
        if (arts == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        if (arts.size() == 0) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }

        double tempPrice = 0;
        for (SimpleGoods good : arts){
            tempPrice = tempPrice + good.getResalePrice();
        }

        hashMap.put("arts",arts);
        hashMap.put("totalPrice", tempPrice);
        return new Result(ResultCode.SUCCESS,hashMap);
    }

    /**
     * 将商品添加到购物车
     */
    @PostMapping("/setShoppingCar")
    public Result setShoppingCar(@RequestBody Map<String, Object> map) {
        Long userId = ParamUtil.tradeToLong(map.get("userId"));
        Long goodId = ParamUtil.tradeToLong(map.get("goodsId"));

        if (userId == null || goodId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        Boolean isSuccess = shoppingCarService.setShoppingCar(userId, goodId);
        if (isSuccess == null || !isSuccess) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 将商品从购物车中删除
     */
    @PostMapping("/deleteShoppingCar")
    public Result deleteShoppingCar(@RequestBody Map<String, Object> map) {
        Long userId = ParamUtil.tradeToLong(map.get("userId"));
        Long goodId = ParamUtil.tradeToLong(map.get("goodsId"));

        if (userId == null || goodId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        Boolean isSuccess = shoppingCarService.deleteShoppingCar(userId, goodId);
        if (isSuccess == null || !isSuccess) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS);
    }
}
