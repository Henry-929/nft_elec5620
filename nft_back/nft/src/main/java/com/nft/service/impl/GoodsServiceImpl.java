package com.nft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nft.entity.ModeFile;
import com.nft.entity.User;
import com.nft.entity.vo.SimpleGoods;
import com.nft.entity.Goods;
import com.nft.mapper.GoodsMapper;
import com.nft.mapper.ModeFileMapper;
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
    @Autowired
    ModeFileMapper modeFileMapper;

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
    public HashMap<String, Object> getGoodDetailsById(Long goodId) {
        HashMap<String, Object> map = new HashMap<>();

        SimpleGoods goodDetailsById = goodsMapper.getGoodDetailsById(goodId);
        if (goodDetailsById == null){
            return null;
        }

        Long artId = goodDetailsById.getArt().getArtId();
        QueryWrapper<ModeFile> wrapper = new QueryWrapper<>();
        wrapper.eq("art_id", artId);
        List<ModeFile> modeFiles = modeFileMapper.selectList(wrapper);

        map.put("SimpleGoods",goodDetailsById);
        map.put("modeFiles",modeFiles);
        return map;
    }

    @Override
    public List<SimpleGoods> getGoodsByKeywords(String keywords) {
        return goodsMapper.getGoodsByKeywords(keywords);
    }

    @Override
    public List<SimpleGoods> getGoodsByPrice(double topPrice, double lowPrice) {
        return goodsMapper.getGoodsByPrice(topPrice,lowPrice);
    }

    @Override
    public int setNotSell(Long goodId,Long artId) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("art_id", artId);
        int delete = goodsMapper.delete(wrapper);
        return delete;
    }

    @Override
    public Goods selectGoodsById(Long goodId) {
        return goodsMapper.selectById(goodId);
    }

    @Override
    public List<SimpleGoods> getAllGoodNoLimit() {
        return goodsMapper.getAllGoodsNoLimit();
    }

    @Override
    public Goods selectGoodsByArtId(Long artId) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("art_id", artId);
        return goodsMapper.selectOne(wrapper);
    }
}
