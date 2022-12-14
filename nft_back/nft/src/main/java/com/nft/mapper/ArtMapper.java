package com.nft.mapper;

import com.nft.entity.vo.SimpleArt;
import com.nft.entity.Art;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * <p>
 * 电子艺术品表 Mapper 接口
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Repository
public interface ArtMapper extends BaseMapper<Art> {

    // 根据artId获取SimpleArt
    SimpleArt getSimpleArtById(Long artId);

    // 获取用户本人的艺术品
    ArrayList<SimpleArt> getOwnerAllArt(Long ownerId, Integer sort);
}
