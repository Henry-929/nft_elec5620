package com.nft.util;

import com.nft.entity.Palette;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class EthUtil {

    private String contractAddress = "0x523d9De49dC6A1eb4e74C741A6d98E87972f53e2";
    private String baseKeyPath = "/Users/mac/IdeaProjects/nft/GETH/keystore";
    private String adminKey = "UTC--2022-03-31T11-44-06.488721200Z--43bb182d89dca344eafaf536079389269ae408d7";
    private String adminPassword = "123";
    private static String url = "http://localhost:8545/";

    private Web3j web3j;

    private static EthUtil ethUtil;


    public static EthUtil getInstance() {
        if (ethUtil == null) {
            synchronized (EthUtil.class) {
                if (ethUtil == null) {
                    ethUtil = new EthUtil();
                }
            }
        }
        return ethUtil;
    }

    private EthUtil() {
    }


    public Web3j getWeb3j() {
        if (web3j == null) {
            synchronized (Web3j.class) {
                if (web3j == null) {
                    web3j = Web3j.build(new HttpService(url));
                }
            }
        }
        return web3j;
    }

    public Palette getPalette() {
        return getPalette(adminPassword, adminKey);
    }

    public Palette getPalette(String password, String keySrc) {
        try {
            Credentials credentials = WalletUtils.loadCredentials(password, baseKeyPath + keySrc);
            return Palette.load(contractAddress, getWeb3j(), credentials, BigInteger.valueOf(21_000l), BigInteger.valueOf(1_000_000l));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendETH(String address, int value) throws Exception {

        Admin web3j = Admin.build(new HttpService());
        //给转账用户解锁
        Credentials credentials = WalletUtils.loadCredentials(adminPassword, baseKeyPath + adminKey);

        PersonalUnlockAccount personalUnlockAccount = web3j.personalUnlockAccount(credentials.getAddress(), adminPassword).send();
        if (personalUnlockAccount.accountUnlocked()) {
            Transaction transaction = new Transaction(credentials.getAddress(), null, null,
                    null, address, new BigInteger(value +"000000000000000000"), null);
            System.out.println("交易的hash:"+web3j.ethSendTransaction(transaction).send().getTransactionHash());
        }
    }
}
