package com.nft.util;

import lombok.Data;

import java.util.List;

@Data
public class Pager<T> {
    private Integer startPage;       // 开始的页数
    private Integer limitNum;        // 结束的记录序数
    private Integer munNum;         // 总共记录数
    private List<T> data;       // 现有的记录数据

}
