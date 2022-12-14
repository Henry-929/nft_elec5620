package com.nft.controller;


import com.nft.entity.*;
import com.nft.entity.vo.SimpleArt;
import com.nft.service.ArtService;
import com.nft.service.FileService;
import com.nft.service.GoodsService;
import com.nft.service.ModeFileService;
import com.nft.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 电子艺术品表 前端控制器
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@RestController
@RequestMapping("/art")
public class ArtController {

    @Autowired
    ArtService artService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    FileService fileService;
    @Autowired
    ModeFileService modeFileService;

    /**
     * 上传一个nft作品（铸币）
     */
    @PostMapping("/uploadArt")
    public Result uploadArt(MultipartFile file, String artName, String artAuthor,
                            String artIntroduction, Long userId) throws IOException {

        if (artName == null || artIntroduction == null || file == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        Long artId = artService.uploadArt(artName,artIntroduction,file,artAuthor,userId);

        if (artId == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,artId);
    }

    /**
     * 同时上传多个mode文件
     */
    @PostMapping("/uploadFiles")
    public Result uploadFiles(MultipartFile[] files, Long artId) throws IOException {

        if (files == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        ArrayList<ModeFile> modeFiles = modeFileService.addModeFile(files,artId);

        if (modeFiles == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,modeFiles);
    }

    /**
     * 获取本人的所有作品
     */
    @PostMapping("/getOwnerAllArt")
    public Result getOwnerAllArt(@RequestBody Map<String, Object> map) {
        Long ownerId = ParamUtil.tradeToLong(map.get("ownerId"));       // ownerId
        Integer sort = ParamUtil.tradeToInteger(map.get("sort"));       // sort  时间排序（1：正序 2：倒序）默认为倒序(null)
        if (ownerId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }
        if (sort == null) sort = 2;

        ArrayList<SimpleArt> arts = artService.getOwnerAllArt(ownerId,sort);

        if (arts.size() == 0) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }
        return new Result(ResultCode.SUCCESS,arts);
    }

    /**
     * 修改作品的基本信息
     */
    @PostMapping("/setArt")
    public Result setArt(@RequestBody Map<String, Object> map) {
        Long artId = ParamUtil.tradeToLong(map.get("artId"));           // artId 艺术品ID
        String artName = (String) map.get("artName");                   // 艺术品名称
        String artIntroduction = (String) map.get("artIntroduction");   // 艺术品简介
        if (artId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        Boolean isSuccess = artService.setArt(artId, artName, artIntroduction);
        if (isSuccess == null) {
            return new Result(ResultCode.EMPTY_CONTENT);
        }
        if (!isSuccess) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 将艺术品设置为出售
     */
    @PostMapping("/setSell")
    public Result setSell(@RequestBody Map<String, Object> map){
        Long ownerId = ParamUtil.tradeToLong(map.get("ownerId"));       // ownerId
        Long artId = ParamUtil.tradeToLong(map.get("artId"));       // artId 艺术品ID
        Double price = ParamUtil.tradeToDouble(map.get("price"));      // 价格

        if (ownerId == null || artId == null || price == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        Long goodsId = artService.setSell(ownerId, artId, price);

        if (goodsId == null) {
            return new Result(ResultCode.SERVER_ERROR);
        }
        return new Result(ResultCode.SUCCESS,goodsId);
    }

    /**
     * 将艺术品设置为不可出售
     */
    @PostMapping("/setNotSell")
    public Result setNotSell(@RequestBody Map<String, Object> map){
        Long artId = ParamUtil.tradeToLong(map.get("artId"));       // artId 艺术品ID

        if (artId == null) {
            return new Result(ResultCode.PARAMETER_NULL_ERROR);
        }

        Goods good = goodsService.selectGoodsByArtId(artId);

        int goodsId = goodsService.setNotSell(good.getGoodsId(),artId);

        if (goodsId < 1) {
            return new Result(ResultCode.NOT_SET_SELL);
        }
        return new Result(ResultCode.SUCCESS);
    }

}
