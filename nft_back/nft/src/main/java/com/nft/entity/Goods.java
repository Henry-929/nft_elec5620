package com.nft.entity;

import java.math.BigDecimal;
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
 * 商品表
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Long goodsId;

    /**
     * 商品艺术品ID
     */
    private Long artId;

    /**
     * 交易模式（1:所有权买卖 2:租赁）
     */
    private Integer tradeMode;

    /**
     * 商品卖家ID
     */
    private Long goodsSellerId;

    /**
     * 如果trade_mode为2，记录租赁开始时间
     */
    private Date rentStartTime;

    /**
     * 如果trade_mode为2，记录租赁结束时间
     */
    private Date rentEndTime;

    /**
     * 零售价
     */
    private BigDecimal resalePrice;

    /**
     * 是否被售出
     */
    private Boolean selled;

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
