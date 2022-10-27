package com.nft.controller;


import com.nft.entity.vo.SimpleGoods;
import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import com.nft.service.ESService;
import com.nft.service.GoodsService;
import com.nft.util.Pager;
import com.nft.util.ParamUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    ESService esService;

    /**
     * 获取所有的nft，根据条件
     */
    @PostMapping("/getAllGoods")
    public Result getAllGoods(@RequestBody Map<String, Integer> map) {
        Integer start = map.get("start");
        Integer limit = map.get("limit");
        if (start == null) start = 1;
        if (limit == null) limit = 10;
        Integer page = (start-1)*limit;

        Pager<SimpleGoods> goods = goodsService.getAllGood(page, limit);

        if (goods.getData().size() == 0) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }
        if (goods == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,goods);
    }

    /**
     * 进入nft的详情页面，获取其详细信息
     */
    @PostMapping("/getDetailsById")
    public Result getDetailsById(@RequestBody Map<String, Object> map) {
        Long goodId = ParamUtil.tradeToLong(map.get("goodId"));
        if (goodId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        SimpleGoods detailsById = goodsService.getGoodDetailsById(goodId);
        if (detailsById == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,detailsById);
    }

    /**
     * 查询活动通过关键字
     */
    @GetMapping("/search/{keywords}")
    public Result searchGoodsByKeywords(@PathVariable("keywords") String keywords) throws IOException {
        if (keywords == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }
        Map<String, Object> map = esService.searchBlogForQuery(1, 10, keywords);

        List<SimpleGoods> arts = (List<SimpleGoods>) map.get("arts");
        if (map == null){
            return new Result(ResultCode.SERVER_ERROR);
        }
        if (arts.size() == 0) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 查询活动通过价格区间
     */
    @GetMapping("/search/{topPrice}/{lowPrice}")
    public Result searchGoodsByPrice(@PathVariable("topPrice") double topPrice,
                                     @PathVariable("lowPrice") double lowPrice) {

        List<SimpleGoods> searchGoods = goodsService.getGoodsByPrice(topPrice,lowPrice);

        if (searchGoods == null){
            return new Result(ResultCode.SERVER_ERROR);
        }
        if (searchGoods.size() == 0) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }
        return new Result(ResultCode.SUCCESS,searchGoods);
    }

}
