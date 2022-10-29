package com.nft.service.impl;

import com.alibaba.fastjson.JSON;
import com.nft.service.EthBlockchainService;
import com.nft.util.EthUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Bip44WalletUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class EthBlockchainServiceImpl implements EthBlockchainService {
    private static Web3j web3j;

    // 生成 Keystore
    @Value("${File.gethPath}")
    String walletFilePath;

    static {
        web3j = EthUtil.getInstance().getWeb3j();
    }

    @Override
    public Map<String, Object> creatEthAddress(String payKey) throws Exception {

        File file = new File(walletFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 在区块链上创造用户，并生成文件
        Bip39Wallet bip39Wallet = Bip44WalletUtils.generateBip44Wallet(payKey, file);

        Credentials credentials = WalletUtils.loadCredentials(payKey, walletFilePath + "/" + bip39Wallet.getFilename());

        // 获取其生成的地址
        String address = credentials.getAddress();

        //解锁账户发起交易。钱包keyStore文件保存在geth节点上,用户发起交易需要解锁账户,
        //转账 1ETH
        //EthUtil.getInstance().sendETH(address, 1);

        // 在以太坊中加载并实施Palette智能合约，将生成的该用户设置为合约的用户
        TransactionReceipt transactionReceipt = EthUtil.getInstance().getPalette()
                .setPower(address)
                .send();
        if (transactionReceipt.getStatus().equals("0x1")) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("address", address);
            map.put("fileName", bip39Wallet.getFilename());
            return map;
        }
        return null;
    }
}
