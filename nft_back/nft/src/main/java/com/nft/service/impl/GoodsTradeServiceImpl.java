package com.nft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nft.entity.GoodsTrade;
import com.nft.entity.User;
import com.nft.mapper.GoodsTradeMapper;
import com.nft.service.GoodsTradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品交易记录表 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class GoodsTradeServiceImpl extends ServiceImpl<GoodsTradeMapper, GoodsTrade> implements GoodsTradeService {

    @Autowired
    GoodsTradeMapper goodsTradeMapper;

    @Override
    public List<GoodsTrade> getBuyOrder(Long userId) {
        QueryWrapper<GoodsTrade> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", userId);
        wrapper.orderByDesc("create_time");
        List<GoodsTrade> goodsTrades = goodsTradeMapper.selectList(wrapper);
        return goodsTrades;
    }

    @Override
    public List<GoodsTrade> getSaleOrder(Long userId) {
        QueryWrapper<GoodsTrade> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", userId);
        wrapper.orderByDesc("create_time");
        List<GoodsTrade> goodsTrades = goodsTradeMapper.selectList(wrapper);
        return goodsTrades;
    }
}
