package org.elastos.dma.dmademo;

import android.support.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.JSON;
import org.elastos.dma.base.entity.eth.EthAccount;
import org.elastos.dma.base.entity.eth.TransactionInfo;
import org.elastos.dma.base.util.HdSupport;
import org.elastos.dma.service.wallet.EthWalletService;
import org.elastos.dma.utility.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
@RunWith(AndroidJUnit4.class)
public class EthWalletTest {


    private String nodeurl = "http://47.105.149.174:8545";
    private EthWalletService ethWalletService = new EthWalletService(nodeurl);

    private BigInteger gasPrice=BigInteger.valueOf(1000000000);
    private BigInteger gasLimit=BigInteger.valueOf(60000);

    /**
    * 创建钱包
    * */
    @Test
    public void createTest() {
        EthAccount ethAccount =ethWalletService.create(HdSupport.MnemonicType.ENGLISH);
        String address = ethAccount.getAddress();//地址
        String mnemonic = ethAccount.getMnemonic();//助记词
        String privateKey = ethAccount.getPrivateKey();//私钥
        String publicKey = ethAccount.getPublicKey();//公钥
        System.out.println(address);
        System.out.println(mnemonic);
        System.out.println(privateKey);
        System.out.println(publicKey);
    }


    /**
    * 根据 助记词导出钱包
    * */
    @Test
    public void exportByMnemonic() throws ApiException {
        String mnemonic = "hospital educate still slam december hidden video what member next raise junior";
        EthAccount ethAccount = ethWalletService.exportByMnemonic(mnemonic);
        String address = ethAccount.getAddress();//地址
        String privateKey = ethAccount.getPrivateKey();//私钥
        String publicKey = ethAccount.getPublicKey();//公钥
        System.out.println(address);
        System.out.println(mnemonic);
        System.out.println(privateKey);
        System.out.println(publicKey);
    }


    /**
    * 根据私钥导出钱包
    * */
    @Test
    public void exportByPrivateKey() throws ApiException {
        String privateKey = "d6ed30f07efa7ccd641186e752f827f9f4901c797d3b614e9f6aee56a031147f";
        EthAccount elaAccount = ethWalletService.exportByPrivateKey(privateKey);
        String address = elaAccount.getAddress();//地址
        String publicKey = elaAccount.getPublicKey();//公钥
        System.out.println(address);
        System.out.println(privateKey);
        System.out.println(publicKey);
    }


    /**
   * 检查地址是否正确
   * */
    @Test
    public void checkAddr() {
        String address = "0x6e55aaebaefe953931fcfc251079b874484e69ed";
        boolean checkAddr = ethWalletService.checkAddr(address);
        System.out.println(checkAddr);
        //I/System.out: true
    }

    /**
  * 获取余额
  * */
    @Test
    public void balance() throws IOException, ApiException {
        String address = ethWalletService.exportByPrivateKey("d6ed30f07efa7ccd641186e752f827f9f4901c797d3b614e9f6aee56a031147f").getAddress();
        String balance = ethWalletService.balance(address);
        System.out.println(balance);
        //I/System.out: 97.924494024
    }

    /***
     * 获取代币余额
     */
    @Test
    public void tokenBalance() throws Exception {
        String owner = "0xe030a4d87b4ec9bdeec972d139f3829a60aaf1d8";//地址
        String contractAddress = "0xc477c556d8a7fa33853dcfc29858932006256f9e";//代币地址
        String tokenBalance = ethWalletService.tokenBalance(owner, contractAddress);
        System.out.println(tokenBalance);

    }


    /***
     * eth转账
     */
    @Test
    public void transfer() throws Exception {
        String privateKey="8194cfe0650f004785b176f83a88af84ed73087a72609174605ef87054272811";
        String _to="0x6e55aaebaefe953931fcfc251079b874484e69ed";
        String _value="100";

        String hash = ethWalletService.transfer(privateKey, _to,_value,gasPrice,gasLimit);
        System.out.println(hash);

    }


    /***
     * eth转账
     */
    @Test
    public void tokenTransfer() throws Exception {
        String contractAddress = "0xd08f65e3895750201c620b1951079e4ee12a251a";//代币地址
        String privateKey="f4b7957d7f11b014a77ad80e5d03d1c732f51c08ec7925c3d6175e19d099d832";
        String _to="0x21f017BA0937426e8d68417b59658e2DC74c8936";
        BigDecimal _value=new BigDecimal("10");
        BigInteger gasPrice=BigInteger.valueOf(1000000000);
        BigInteger gasLimit=BigInteger.valueOf(60000);
        String hash = ethWalletService.tokenTransfer(contractAddress,privateKey, _to,_value,gasPrice,gasLimit);
        System.out.println(hash);


    }

    /**
 * 获取交易状态
 * */
    @Test
    public void getTransferStatus() throws IOException {
        String txid = "0x101f5d52a735cbcc90f66a1de240da9f13f6fea5ff4c5319ed9f286fd2b5765c";
        Boolean transferStatus = ethWalletService.getStatusByHash(txid);
        System.out.println(transferStatus);
        //I/System.out: true
    }

    /**
* 获取交易信息
* */
    @Test
    public void transactionInfo() throws IOException {
        String hash = "0x101f5d52a735cbcc90f66a1de240da9f13f6fea5ff4c5319ed9f286fd2b5765c";
        TransactionInfo transactionInfo = ethWalletService.transactionInfo(hash);
        System.out.println(JSON.toJSONString(transactionInfo));
        //I/System.out: {"blockHash":"0x2a1cff7db9a16fcf0da26848cc05a4b4ae0b4bc740f1bfac86f0573e2eaa3f03","blockNumber":"1157177","from":"0x9be170325c327292b6c3f145e0a74cc51b97b41f","gasLimit":7002513,"gasPrice":"0.00000002","gasUsed":"29545","logs":[{"address":"0x539557d1888589bf1032a2c9f1b952a4d838781d","blockHash":"0x2a1cff7db9a16fcf0da26848cc05a4b4ae0b4bc740f1bfac86f0573e2eaa3f03","blockNumber":1157177,"blockNumberRaw":"0x11a839","data":"0x0000000000000000000000000000000000000000000000004563918244f40000","logIndex":0,"logIndexRaw":"0x0","removed":false,"topics":["0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef","0x00000000000000000000000023fb54a9050b5b765ada7ff5b84abab9dd4d87bd","0x000000000000000000000000c9a244de74649a3e31e36c202706145e83e05458"],"transactionHash":"0x101f5d52a735cbcc90f66a1de240da9f13f6fea5ff4c5319ed9f286fd2b5765c","transactionIndex":0,"transactionIndexRaw":"0x0"}],"nonce":372,"status":"1","timestamp":1563272880,"to":"0x23fb54a9050b5b765ada7ff5b84abab9dd4d87bd","transactionHash":"0x101f5d52a735cbcc90f66a1de240da9f13f6fea5ff4c5319ed9f286fd2b5765c","transactionIndex":"0","value":"0"}
    }


}
