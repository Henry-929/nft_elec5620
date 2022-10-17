package com.nft.service.impl;

import com.nft.entity.vo.SimpleGoods;
import com.nft.entity.Goods;
import com.nft.mapper.GoodsMapper;
import com.nft.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Pager<SimpleGoods> getAllGood(Integer start, Integer limit) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("start", start);
        hashMap.put("limit", limit);
        Pager<SimpleGoods> simpleGoodsPager = new Pager<>();
        simpleGoodsPager.setData(goodsMapper.getAllGoods(hashMap));
        simpleGoodsPager.setStartPage(start);
        simpleGoodsPager.setLimitNum(limit);
        return simpleGoodsPager;
    }

    @Override
    public SimpleGoods getGoodDetailsById(Long goodId) {
        return goodsMapper.getGoodDetailsById(goodId);
    }

    @Override
    public List<SimpleGoods> getGoodsByKeywords(String keywords) {
        return goodsMapper.getGoodsByKeywords(keywords);
    }

    @Override
    public List<SimpleGoods> getGoodsByPrice(double topPrice, double lowPrice) {
        return goodsMapper.getGoodsByPrice(topPrice,lowPrice);
    }
}
