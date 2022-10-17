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
 * 电子艺术品表
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Art implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 艺术品ID
     */
    @TableId(value = "art_id", type = IdType.AUTO)
    private Long artId;

    /**
     * 艺术品名称
     */
    private String artName;

    /**
     * 电子艺术品源文件
     */
    private Long artFile;

    /**
     * 艺术品简介
     */
    private String artIntroduction;

    /**
     * 在区块链中的token
     */
    private String artToken;

    /**
     * 艺术品作者
     */
    private String artAuthor;

    /**
     * 艺术品拥有者id
     */
    private Long ownerId;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

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
