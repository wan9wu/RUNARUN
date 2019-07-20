package org.elastos.dma.dmademo;

import android.support.test.runner.AndroidJUnit4;

import org.elastos.dma.service.merchant.ETHPledgeActivityService;
import org.elastos.dma.service.merchant.impl.ETHPledgeActivityServiceImpl;
import org.elastos.dma.utility.util.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YangChuanTong
 * @Date: 2019/7/17 18:42
 * @Description:
 */
@RunWith(AndroidJUnit4.class)
public class ETHPledgeActivityTest {

    String nodeurl = "http://192.168.1.104:8545";//节点地址
    ETHPledgeActivityService ethPledgeActivityService = new ETHPledgeActivityServiceImpl(nodeurl);
    private BigInteger gasPrice = BigInteger.valueOf(1000000000);
    private BigInteger gasLimit = BigInteger.valueOf(6000000);


    //生成者私钥
    private String producerPrivateKey = "8207158f2e65deeab25533ff9a6effd81ac2341fcbbfa52903165a533c3e7067";
    //生成者地址
    private String producerAddress = "0xE8e53eC9136194590D7EB489661773b6e2139A96";


    //用户A私钥
    private String userPrivateKeyByA = "3bcad3ff11b0fe5fad05fc61273b4a93e30038ccbeaace38a35dc9221d7c73e0";
    //用户A地址
    private String userAddressByA = "0xFeb76f728cFcfFD7b1c03396599259b7B46221D4";

    //用户B私钥
    private String userPrivateKeyByB = "e832104fd82118ce0326f2671c922c081132f5b0f9b94f199b03b3ae30b9dbc2";
    //用户B地址
    private String userAddressByB = "0x586cc89A9EAB07164cB18c0d90Ac4e38493d55b5";

    //资产合约
    private String assetAddress = "0x9676adbfa28f29eb97ad3dd4e3b7ea5159c645dc";
    //活动合约
    private String platformAddress = "0x2cc7fae36bf8de3ead05665cc14d99ec76fd90c0";
    /**
     * 创建合约
     */
    @Test
    public void deploy() {
        String name = "testAsset";//资产合约名字
        String symbol = "TA";//资产合约简称
        String metadata = "testMetadata"; //资产合约信息
        Long endTime = 1563527410L;//活动结束时间戳
        JsonResult<ETHPledgeActivityServiceImpl.DeployResult> jsonResult = ethPledgeActivityService.deploy(producerPrivateKey, gasPrice, gasLimit, name, symbol, metadata, endTime);
        System.out.println(jsonResult.getData().getAssetAddress());//资产合约
        System.out.println(jsonResult.getData().getPledgeActivityAddress()); //活动合约
    }


    /**
     * 创建资产（活动结束时间之前）
     */
    @Test
    public void mint() {
        String metadata = "test";
        List<BigInteger> tokenIds = new ArrayList<>();
        tokenIds.add(BigInteger.valueOf(1000));
        tokenIds.add(BigInteger.valueOf(1001));
        tokenIds.add(BigInteger.valueOf(1002));
        tokenIds.add(BigInteger.valueOf(1003));

        JsonResult<String> jsonResult = ethPledgeActivityService.mint(assetAddress, producerPrivateKey, gasPrice, gasLimit, producerAddress, metadata, tokenIds, true, true);
        System.out.println(jsonResult.getData());//交易hash

    }


    /**
     * 生成者资产上架（活动结束时间之前）
     */
    @Test
    public void OnSalesByProducer() {
        List<BigInteger> tokenIds = new ArrayList<>();
        tokenIds.add(BigInteger.valueOf(1000));
        tokenIds.add(BigInteger.valueOf(1001));
        tokenIds.add(BigInteger.valueOf(1002));
        tokenIds.add(BigInteger.valueOf(1003));
        BigDecimal price = new BigDecimal("1");
        JsonResult<ETHPledgeActivityServiceImpl.OnSaleResult> jsonResult = ethPledgeActivityService.onSales(assetAddress, platformAddress, producerPrivateKey, gasPrice, gasLimit, tokenIds, price, producerAddress);
        System.out.println(jsonResult.getData().getApproveHash());//资产授权交易hash
        System.out.println(jsonResult.getData().getSaveApproveHash());//资产上架交易hash

    }


    /**
     * 生成者资产下架（活动结束时间之前）
     */
    @Test
    public void offSalesByProducer() {
        List<BigInteger> tokenIds = new ArrayList<>();
        tokenIds.add(BigInteger.valueOf(1000));
        tokenIds.add(BigInteger.valueOf(1001));
        tokenIds.add(BigInteger.valueOf(1002));
        tokenIds.add(BigInteger.valueOf(1003));
        JsonResult<String> jsonResult = ethPledgeActivityService.offSales(platformAddress, producerPrivateKey, gasPrice, gasLimit, tokenIds);
        System.out.println(jsonResult.getData());//资产下架交易hash

    }


    /**
     * 用户A购买商品创建订单（活动结束时间之前）
     */
    @Test
    public void createOrderByUserA() {
        BigInteger tokenid = BigInteger.valueOf(1003);

        BigDecimal price = new BigDecimal("1");
        JsonResult<String> jsonResult = ethPledgeActivityService.createOrder(platformAddress, userPrivateKeyByA, gasPrice, gasLimit, tokenid, price, producerAddress);
        System.out.println(jsonResult.getData());//购买交易hash
    }


    /**
     * 用户A资产转卖上架（活动结束时间之前）
     */
    @Test
    public void OnSalesByUserA() {
        List<BigInteger> tokenIds = new ArrayList<>();
        tokenIds.add(BigInteger.valueOf(1000));
        tokenIds.add(BigInteger.valueOf(1001));
        tokenIds.add(BigInteger.valueOf(1002));
        tokenIds.add(BigInteger.valueOf(1003));
        BigDecimal price = new BigDecimal("2");
        JsonResult<ETHPledgeActivityServiceImpl.OnSaleResult> jsonResult = ethPledgeActivityService.onSales(assetAddress, platformAddress, userPrivateKeyByA, gasPrice, gasLimit, tokenIds, price, userAddressByA);
        System.out.println(jsonResult.getData().getApproveHash());//资产授权交易hash
        System.out.println(jsonResult.getData().getSaveApproveHash());//资产上架交易hash

    }


    /**
     * 用户A资产下架（活动结束时间之前）
     */
    @Test
    public void offSalesByUserA() {
        List<BigInteger> tokenIds = new ArrayList<>();
        tokenIds.add(BigInteger.valueOf(1000));
        tokenIds.add(BigInteger.valueOf(1001));
        tokenIds.add(BigInteger.valueOf(1002));
        tokenIds.add(BigInteger.valueOf(1003));
        JsonResult<String> jsonResult = ethPledgeActivityService.offSales(platformAddress, userPrivateKeyByA, gasPrice, gasLimit, tokenIds);
        System.out.println(jsonResult.getData());//资产下架交易hash

    }


    /**
     * 用户B购买商品创建订单（活动结束时间之前）
     */
    @Test
    public void createOrderByUserB() {
        BigInteger tokenid = BigInteger.valueOf(1003);
        BigDecimal price = new BigDecimal("2");
        JsonResult<String> jsonResult = ethPledgeActivityService.createOrder(platformAddress, userPrivateKeyByB, gasPrice, gasLimit, tokenid, price, userAddressByA);
        System.out.println(jsonResult.getData());//购买交易hash
    }


    /**
     * 生产者结束活动，获取收取的eth（活动结束时间之后）
     */
    @Test
    public void endActivity() {
        JsonResult<String> jsonResult = ethPledgeActivityService.endActivity(platformAddress, producerPrivateKey, gasPrice, gasLimit);
        System.out.println(jsonResult.getData());//交易hash

    }

    /**
     * 验证签到后退还押金
     */
    @Test
    public void verify() {
        BigInteger tokenid = BigInteger.valueOf(1000);
        String owner = userAddressByB;
        JsonResult<String> jsonResult = ethPledgeActivityService.verify(platformAddress, producerPrivateKey, gasPrice, gasLimit, tokenid, owner);
        System.out.println(jsonResult.getData());//交易hash

    }

    /**
     * 发起方主动退款
     */
    @Test
    public void refund() {
        BigInteger tokenid = BigInteger.valueOf(1001);
        JsonResult<String> jsonResult = ethPledgeActivityService.refund(platformAddress, producerPrivateKey, gasPrice, gasLimit, tokenid);
        System.out.println(jsonResult.getData());//交易hash

    }


    /**
     * 获取结束时间
     */
    @Test
    public void getEndTime() {
        JsonResult<Long> jsonResult = ethPledgeActivityService.getEndTime(platformAddress);
        System.out.println(jsonResult.getData());//交易hash

    }

}
