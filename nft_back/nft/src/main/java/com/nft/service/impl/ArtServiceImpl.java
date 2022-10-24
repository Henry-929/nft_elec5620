package com.nft.service.impl;

import com.nft.entity.Art;
import com.nft.entity.File;
import com.nft.entity.Goods;
import com.nft.entity.vo.SimpleArt;
import com.nft.mapper.ArtMapper;
import com.nft.service.ArtService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.service.FileService;
import com.nft.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * <p>
 * 电子艺术品表 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class ArtServiceImpl extends ServiceImpl<ArtMapper, Art> implements ArtService {

    @Autowired
    FileService fileService;
    @Autowired
    ArtMapper artMapper;
    @Autowired
    GoodsService goodsService;

    @Override
    public Long uploadArt(String artName, String artIntroduction, MultipartFile multipartFile,
                          String artAuthor, Long userId) throws IOException {
        // 需要新建一个nft的字段
        Art art = new Art();
        art.setArtName(artName);
        art.setArtIntroduction(artIntroduction);
        art.setArtAuthor(artAuthor);
        art.setOwnerId(userId);

        File file = fileService.addFile(multipartFile);
        art.setArtFile(file.getFileId());

        // 将nft类存储到数据库中
        if (artMapper.insert(art) == 0) {
            return null;
        }

        return art.getArtId();
    }

    @Override
    public ArrayList<SimpleArt> getOwnerAllArt(Long ownerId, Integer sort) {
        return artMapper.getOwnerAllArt(ownerId, sort);
    }

    @Override
    public Boolean setArt(Long artId, String artName, String artIntroduction) {
        Art art = artMapper.selectById(artId);
        if (art == null)
            return null;

        Art tempA = new Art();
        tempA.setArtId(artId);
        tempA.setArtName(artName);
        tempA.setArtIntroduction(artIntroduction);
        int i = artMapper.updateById(tempA);
        return i > 0;
    }

    @Override
    public Long setSell(Long ownerId, Long artId, Double price) {
        Art art = artMapper.selectById(artId);
        if (art == null)
            return null;

        Goods goods = new Goods();
        goods.setArtId(artId);
        goods.setGoodsSellerId(ownerId);
        goods.setSelled(false);
        goods.setResalePrice(new BigDecimal(price));

        if (goodsService.getBaseMapper().insert(goods) == 0) {
            return null;
        }
        return goods.getGoodsId();
    }
}
