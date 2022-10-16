package com.nft.service;

import com.nft.entity.vo.SimpleGoods;
import com.nft.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nft.util.Pager;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
public interface GoodsService extends IService<Goods> {

    // 获取所有的nft，
    Pager<SimpleGoods> getAllGood(Integer start, Integer limit);

}
