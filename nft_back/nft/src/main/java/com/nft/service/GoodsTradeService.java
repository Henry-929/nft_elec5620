package com.nft.service;

import com.nft.entity.GoodsTrade;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品交易记录表 服务类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
public interface GoodsTradeService extends IService<GoodsTrade> {

    List<GoodsTrade> getBuyOrder(Long userId);

    List<GoodsTrade> getSaleOrder(Long userId);
}
