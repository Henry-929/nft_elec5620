package com.nft.mapper;

import com.nft.entity.vo.SimpleGoods;
import com.nft.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    // 获取某个类型的所有艺术品
    ArrayList<SimpleGoods> getAllGoods(HashMap<String,Object> map);

    // 获取good详情by id
    SimpleGoods getGoodDetailsById(Long goodId);

    // 搜索通过关键词
    List<SimpleGoods> getGoodsByKeywords(String keywords);

    //搜索通过价格
    List<SimpleGoods> getGoodsByPrice(double topPrice, double lowPrice);

    List<SimpleGoods> getAllGoodsNoLimit();
}
