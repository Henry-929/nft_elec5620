package com.nft.mapper;

import com.nft.entity.vo.SimpleGoods;
import com.nft.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

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

}
