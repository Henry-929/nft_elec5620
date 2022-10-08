package com.nft.mapper;

import com.nft.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}
