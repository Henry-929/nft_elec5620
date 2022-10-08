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
 * 商品交易记录表
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易记录ID
     */
    @TableId(value = "trade_id", type = IdType.ASSIGN_ID)
    private Long tradeId;

    /**
     * 商品的ID
     */
    private Long goodsId;

    /**
     * 卖方ID
     */
    private Long sellerId;

    /**
     * 买方ID
     */
    private Long buyerId;

    /**
     * 涉及金额
     */
    private BigDecimal coin;

    /**
     * 手续费
     */
    private BigDecimal serviceCharge;

    /**
     * 交易方式（1:零售 2:拍卖 3:盲盒）
     */
    private Integer tradeType;

    /**
     * 如果为非零售，存储其方式ID(拍卖、盲盒)，如果为零售，存储其商品ID
     */
    private Long typeId;

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
