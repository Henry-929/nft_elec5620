package com.nft.service;

import com.nft.entity.vo.SimpleGoods;
import com.nft.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nft.util.Pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    //获取good详情by id
    HashMap<String, Object> getGoodDetailsById(Long goodId);

    //搜索通过关键词
    List<SimpleGoods> getGoodsByKeywords(String keywords);

    //搜索通过价格
    List<SimpleGoods> getGoodsByPrice(double topPrice, double lowPrice);

    //设置nft不可出售
    int setNotSell(Long goodId,Long artId);

    Goods selectGoodsById(Long goodId);

    List<SimpleGoods> getAllGoodNoLimit();

    Goods selectGoodsByArtId(Long artId);
}
