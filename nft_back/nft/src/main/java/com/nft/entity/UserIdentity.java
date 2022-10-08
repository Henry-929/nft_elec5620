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
 * 身份表
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserIdentity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_identity_id", type = IdType.ASSIGN_ID)
    private Long userIdentityId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户身份(1:审核员 2:管理员 3:工作人员)
     */
    private Integer userIdentity;

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
