package org.elastos.dma.dmademo;

import android.support.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.JSON;

import org.elastos.dma.base.entity.ela.ElaAccount;
import org.elastos.dma.base.util.HdSupport;
import org.elastos.dma.base.util.ela.HttpKit;
import org.elastos.dma.service.wallet.ElaWalletService;
import org.elastos.dma.utility.entity.ela.ChainType;
import org.elastos.dma.utility.entity.ela.TransactionInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.Collections;

import io.github.novacrypto.DatatypeConverter;
@RunWith(AndroidJUnit4.class)
public class ElaWalletTest {


     private String nodeurl = "http://54.64.220.165:21334";//亦来云测试节点地址
//    private String nodeurl = "https://node-mainnet-restful.elastos.org";//亦来云测试节点地址

    private ElaWalletService elaWalletService = new ElaWalletService(nodeurl);


    /**
     * 创建钱包
     **/
    @Test
    public void create() {
        ElaAccount elaAccount = elaWalletService.create(HdSupport.MnemonicType.ENGLISH);
        String address = elaAccount.getAddress();//地址
        String mnemonic = elaAccount.getMnemonic();//助记词
        String privateKey = elaAccount.getPrivateKey();//私钥
        String publicKey = elaAccount.getPublicKey();//公钥
        System.out.println(address);
        System.out.println(mnemonic);
        System.out.println(privateKey);
        System.out.println(publicKey);

        //I/System.out: EaVa5uaaBs7MfgQo2SD8pzWyieUvadRTP6
        //I/System.out: response develop glow flower castle train cluster bamboo cabbage logic arm act
        //I/System.out: D210FD93B4ED271DD784C8B8A3D0E15D6A64EEC671DFF5542AAD19A55206C531
        //I/System.out: 02CD6CAE3F78853AD88CCE9C24F98C019A4F40B3E942A1EA35024002CE8F034BD1
    }


    /**
     * 根据 助记词导出钱包
     */
    @Test
    public void exportByMnemonic() {
        String mnemonic = "artist reject lobster torch antenna fame flower develop outside chief theory aisle";
        ElaAccount elaAccount = elaWalletService.exportByMnemonic(mnemonic);
        String address = elaAccount.getAddress();//地址
        String privateKey = elaAccount.getPrivateKey();//私钥
        String publicKey = elaAccount.getPublicKey();//公钥
        System.out.println(address);
        System.out.println(mnemonic);
        System.out.println(privateKey);
        System.out.println(publicKey);

        //I/System.out: EJ4UGgXyKwrG5s6Bo25ATZvso8BXpG4Hu5
        //I/System.out: artist reject lobster torch antenna fame flower develop outside chief theory aisle
        //I/System.out: 7CE3BF09106A1BD70EC3F9422994B4BDF12C1721D99759B969C5C3773E741200
        //I/System.out: 030205B6A2F80265B6AA9B74916F5F1E4997F53A68932AB771CFCF9977AD2F9C85
    }


    /**
     * 根据私钥导出钱包
     */
    @Test
    public void exportByPrivateKey() {
        String privateKey = "24DC19B679F3D4310B729CABCD1D7793AC822E32043F996FCF26614D92CD1B9E";
        ElaAccount elaAccount = elaWalletService.exportByPrivateKey(privateKey);
        String address = elaAccount.getAddress();//地址
        String publicKey = elaAccount.getPublicKey();//公钥
        System.out.println(address);
        System.out.println(privateKey);
        System.out.println(publicKey);

        //I/System.out: ESKQEz6qxxc1zWM9ey5axmEGqA6ceJ9QGE
        //I/System.out: 24DC19B679F3D4310B729CABCD1D7793AC822E32043F996FCF26614D92CD1B9E
        //I/System.out: 02F076C9C733693F7B7E18946D4C4718B314C6F9080F1DF4A5A8163D215A8083FD
    }


    /**
     * 检查地址是否正确
     */
    @Test
    public void checkAddr() {
        String address = "EV4UTSLGpFMM8Y5jjZp4TLVc9QaNFHGosC";
        boolean checkAddr = elaWalletService.checkAddr(address);
        System.out.println(checkAddr);
        //I/System.out: true
    }

    /**
     * 获取余额
     */
    @Test
    public void balance() {
        String address = "EVix3FiopFNEEF5MiuHyEJH9XMfwoSUkst";
        String balance = elaWalletService.balance(address);
        System.out.println(balance);
    }


    /**
     * 转账
     */
    @Test
    public void transfer() throws Exception {
        String privateKey = "24DC19B679F3D4310B729CABCD1D7793AC822E32043F996FCF26614D92CD1B9E";
        String to = "EVix3FiopFNEEF5MiuHyEJH9XMfwoSUkst";
        String value = "0.001";

        String memo = "type:text,msg:转账";
        String txid = elaWalletService.transfer(privateKey, to, new BigDecimal(value), memo);
        System.out.println(txid);

        //
    }

    @Test
    public void batchTransfer() throws Exception {
        String privateKey = "4CDA5C9D041CD0281E5724A106B84977071A454FD56EA1294985DE76210073EA";
        String to = "ENHFT9Xuo3Z1bJn5d2pLfpndwVez7sVG5H";
        String value = "0.1";
        String txid = elaWalletService.transferWithArray(privateKey, Collections.singletonList(to), Collections.singletonList(new BigDecimal(value)),"test");
        System.out.println(txid);
    }

    /**
     * 获取交易记录
     */
    @Test
    public void getBatchTransferInfo() {
        String txid = "b2b78a15151d999c203fb5873806090c802e8c53b13894287d913df052150387";
        //String txid = "ba1c248314734dc99700d4475f0cb305b17c4fb1f09cbd8824c3835539e0124f";
        // 74657374
        TransactionInfo transactionInfo = elaWalletService.transactionInfo(txid);
        System.out.println(JSON.toJSONString(transactionInfo));
    }



    /**
     * 获取交易状态
     */
    @Test
    public void getTransferStatus() {
        String txid = "0a5b0e4e20ff0f3d3c24aa1e4bf62094c8f13803ae85a7c4a9e4f5c529cf8d3b";
        Boolean transferStatus = elaWalletService.getStatusByHash(txid);
        System.out.println(transferStatus);
    }


    @Test
    public void testParse() {

        String code = "747970653a746578742c6d73673a46726f6d20454c4142616e6b2c456e74657220456c6173746f73202d20546974616e20205375706572204e6f64652052657761726420446973747269627574696f6e";
        byte[] program = DatatypeConverter.parseHexBinary(code);
        String str2 = new String(program);
        System.out.println(str2);


    }


    /**
     * 签名交易
     */
    @Test
    public void getRawTx() throws Exception {
        String privateKey = "4CDA5C9D041CD0281E5724A106B84977071A454FD56EA1294985DE76210073EA";
        String to = "ENHFT9Xuo3Z1bJn5d2pLfpndwVez7sVG5H";
        String value = "0.1";
        String txCode = elaWalletService.getRawTx(privateKey, to, new BigDecimal(value), "测试");
        System.out.println(txCode);
    }


    /**
     * 解析交易签名
     */
    @Test
    public void decodeRawTx() throws Exception {
        String txCode = "020001001337303131303438383338323035323538353537012F08CBCC8616F251F04621A481F24DC5287EEFA1A916A2E8FDEC5365B132A84201000000000002B037DB964A231458D2D6FFD5EA18944C4F90E63D547C5D3B9874DF66A4EAD0A380969800000000000000000021383C336427B1FA251CC48B5A9C1069226E44B3DDB037DB964A231458D2D6FFD5EA18944C4F90E63D547C5D3B9874DF66A4EAD0A3602DC304000000000000000021829A879DD9936EAF5A892514F9279AD0DB3F98B3000000000141402A351EE9120B2D3BFEEF2F1B1AA0F53D75F5D8041BFAA3FC5309461561C0180BA8AD2942AC4AC5517CD93E5CABD95AA2A1D029CEA688168EA9CBA514C8F3419C2321038BA51F7221E0660CE83EBDCC6A9BC1581C36B895F367DB34AB66CBE56DC6D61BAC";
        String result = elaWalletService.decodeRawTx(txCode);
        System.out.println(result);
    }


    /**
     * 发送交易签名上链
     */
    @Test
    public void sendTx() throws Exception {
        String txCode = "020001001337303131303438383338323035323538353537012F08CBCC8616F251F04621A481F24DC5287EEFA1A916A2E8FDEC5365B132A84201000000000002B037DB964A231458D2D6FFD5EA18944C4F90E63D547C5D3B9874DF66A4EAD0A380969800000000000000000021383C336427B1FA251CC48B5A9C1069226E44B3DDB037DB964A231458D2D6FFD5EA18944C4F90E63D547C5D3B9874DF66A4EAD0A3602DC304000000000000000021829A879DD9936EAF5A892514F9279AD0DB3F98B3000000000141402A351EE9120B2D3BFEEF2F1B1AA0F53D75F5D8041BFAA3FC5309461561C0180BA8AD2942AC4AC5517CD93E5CABD95AA2A1D029CEA688168EA9CBA514C8F3419C2321038BA51F7221E0660CE83EBDCC6A9BC1581C36B895F367DB34AB66CBE56DC6D61BAC";
        String result = elaWalletService.sendTx(txCode, ChainType.MAIN_CHAIN);
        System.out.println(result);
    }




}
