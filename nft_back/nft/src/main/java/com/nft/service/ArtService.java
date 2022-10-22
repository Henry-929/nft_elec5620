package com.nft.service;

import com.nft.entity.Art;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nft.entity.vo.SimpleArt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>
 * 电子艺术品表 服务类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
public interface ArtService extends IService<Art> {

    // 上传一个nft
    Long uploadArt(String artName, String artIntroduction, MultipartFile file, String artAuthor, Long userId) throws IOException;

    // 获取用户本人的艺术品
    ArrayList<SimpleArt> getOwnerAllArt(Long ownerId, Integer sort);

    // 修改艺术品基本信息
    Boolean setArt(Long artId, String artName, String artIntroduction);

    // 将艺术品设置为出售状态
    Long setSell(Long ownerId, Long artId, Double price, String payKey);
}
