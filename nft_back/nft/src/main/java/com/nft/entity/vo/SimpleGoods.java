package com.nft.entity.vo;

import com.nft.entity.Art;
import com.nft.entity.File;
import com.nft.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class SimpleGoods {

    private Long goodsId;
    private Boolean selled;
    private User goodsSeller;
    private Double resalePrice;
    private Art art;
    private File file;

}
