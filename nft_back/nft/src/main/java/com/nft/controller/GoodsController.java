package com.nft.controller;


import com.nft.controller.vo.SimpleGoods;
import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import com.nft.service.GoodsService;
import com.nft.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    /**
     * 获取所有的nft，根据条件
     *
     * @param map
     * @return
     */
    @PostMapping("/getAllGoods")
    public Result getAllGoods(@RequestBody Map<String, Object> map) {
        Integer start = (Integer) map.get("start");
        Integer limit = (Integer) map.get("limit");
        if (start == null) start = 0;
        if (limit == null) limit = 10;

        Pager<SimpleGoods> arts = goodsService.getAllGood(start, limit);

        if (arts == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,arts);
    }

}
