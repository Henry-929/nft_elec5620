package com.nft.mapper;

import com.nft.entity.CommentLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 评论点赞记录 Mapper 接口
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Repository
public interface CommentLikeMapper extends BaseMapper<CommentLike> {

}
