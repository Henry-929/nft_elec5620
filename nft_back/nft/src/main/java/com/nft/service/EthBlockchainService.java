package com.nft.service;

import java.util.Map;

public interface EthBlockchainService {

    // 创造以太坊地址
    Map<String, Object> creatEthAddress(String payKey) throws Exception;
}
