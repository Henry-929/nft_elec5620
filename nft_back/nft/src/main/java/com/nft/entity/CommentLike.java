package com.nft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论点赞记录
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommentLike implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论点赞ID
     */
    @TableId(value = "comment_like_id", type = IdType.ASSIGN_ID)
    private Long commentLikeId;

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 点赞者
     */
    private Long likeUserId;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    /**
     * 创造时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
