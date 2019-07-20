package org.elastos.dma.dmademo.tool;

import org.elastos.dma.dmademo.service.AccountService;
import org.elastos.dma.dmademo.service.AccountServiceImpl;
import org.elastos.dma.service.merchant.ETHMerchantService;
import org.elastos.dma.service.merchant.impl.ETHMerchantServiceImpl;
import org.elastos.dma.service.passport.PassportService;
import org.elastos.dma.service.passport.impl.PassportServiceImpl;
import org.elastos.dma.service.wallet.ElaWalletService;
import org.elastos.dma.service.wallet.EthWalletService;

/**
 * Created by xianxian on 2018/3/20.
 */

public class NodeClient {
    public static String didip = "http://54.64.220.165:21604";//测试 http://did-mainnet-node-lb-1452309420.ap-northeast-1.elb.amazonaws.com:20604
    public static String elaip = "http://54.64.220.165:21334";
    public static String ip = "http://47.105.136.158:8545";//DMAtestlink
    public static String ipfsIP="http://47.105.136.158:8080";//ipfs生产
    private volatile static EthWalletService ethWalletService;
    private volatile static PassportService passportService;
    private volatile static ElaWalletService elaWalletService;
    private volatile static AccountService accountService;
    private volatile  static ETHMerchantService ethMerchantService;



    public static EthWalletService getClient() {
        if (ethWalletService == null) {
            ethWalletService = new EthWalletService(ip);
        }
        return ethWalletService;
    }

    public static AccountService getAccountClient() {
        if (accountService == null) {
            accountService = new AccountServiceImpl(didip);
        }
        return accountService;
    }

    public static PassportService getPassportService() {
        if (passportService == null) {
            passportService = new PassportServiceImpl(didip);
        }
        return passportService;
    }

    public static ElaWalletService getElaServiceClient() {
        if (elaWalletService == null) {
            elaWalletService = new ElaWalletService(elaip );
        }
        return elaWalletService;
    }
    public static ETHMerchantService getEthMerchantService() {
        if (ethMerchantService == null) {
            ethMerchantService = new ETHMerchantServiceImpl(ip );
        }
        return ethMerchantService;
    }


}
