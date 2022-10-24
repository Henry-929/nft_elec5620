package com.nft.service;

import com.nft.entity.GoodsTrade;
import com.nft.entity.ShoppingCar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
public interface ShoppingCarService extends IService<ShoppingCar> {

    GoodsTrade buyGoodsById(Long userId, Long goodsId, String payKey);
}
