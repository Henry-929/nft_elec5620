package com.nft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * token表
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "token_id", type = IdType.ASSIGN_ID)
    private Long tokenId;

    private Long token;

    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Date deadlineTime;


}
