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
 * 商品租赁
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsRent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租赁ID
     */
    @TableId(value = "rent_id", type = IdType.ASSIGN_ID)
    private Long rentId;

    private Long goodsId;

    /**
     * 租赁人 收钱的一方
     */
    private Long leaseholderId;

    /**
     * 租客 出钱的一方
     */
    private Long tenantId;

    /**
     * 租金
     */
    private BigDecimal charterMoney;

    /**
     * 手续费
     */
    private BigDecimal serviceCharge;

    /**
     * 交易方式（1:零售 2:拍卖 3:盲盒）
     */
    private Integer tradeType;

    /**
     * 如果为非零售，存储其方式ID，如果为零售，存储其商品ID
     */
    private Long typeId;

    /**
     * 租期开始时间
     */
    private Date rentStartTime;

    /**
     * 租期结束时间
     */
    private Date rentEndTime;

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
