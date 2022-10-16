package com.nft.controller.vo;

import com.nft.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class SimpleGoods {

    private Long goodsId;
    private Integer tradeMode;
    private User goodsSeller;
    private Date rentStartTime;
    private Date rentEndTime;
    private Double resalePrice;
    private SimpleArt simpleArt;

}
