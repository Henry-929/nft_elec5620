package com.nft.service.impl;

import com.nft.entity.Comment;
import com.nft.mapper.CommentMapper;
import com.nft.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
