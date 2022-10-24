package com.nft.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nft.entity.GoodsTrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nft.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品交易记录表 Mapper 接口
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Repository
public interface GoodsTradeMapper extends BaseMapper<GoodsTrade> {

}
