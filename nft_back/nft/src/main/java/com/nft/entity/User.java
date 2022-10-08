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
 * 用户表
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 密码，数据库中通过保存通过SHA-256处理过的值
     */
    private String userPassword;

    /**
     * 手机号码
     */
    private String userPhone;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像图片文件的ID
     */
    private Long userHeadImageId;

    /**
     * 是否已经跳过教程
     */
    private Boolean skipCourse;

    /**
     * 以太坊地址
     */
    private String ethAddress;

    /**
     * key文件名
     */
    private String keySrc;

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
