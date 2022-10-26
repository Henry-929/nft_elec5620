package com.nft.mapper;

import com.nft.entity.ShoppingCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nft.entity.vo.SimpleGoods;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Repository
public interface ShoppingCarMapper extends BaseMapper<ShoppingCar> {

    ArrayList<SimpleGoods> getShoppingCar(Long userId);
}
