package org.elastos.dma.test;

import com.alibaba.fastjson.JSON;
import org.elastos.dma.base.entity.eth.EthAccount;
import org.elastos.dma.base.entity.eth.TransactionInfo;
import org.elastos.dma.base.util.HdSupport;
import org.elastos.dma.service.wallet.EthWalletService;
import org.elastos.dma.utility.exception.ApiException;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class EthWalletTest {


    private String nodeurl = "http://52.205.30.16:8545";
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
    }

    /**
  * 获取余额
  * */
    @Test
    public void balance() throws IOException {
        String address = "0xa2177aaa258a453036dc4939a6661f8756ef33e6";
        String balance = ethWalletService.balance(address);
        System.out.println(balance);
    }

    /***
     * 获取代币余额
     */
    @Test
    public void tokenBalance() throws Exception {
        String owner = "0xA258f950203b46A4036CE4c71BC64CE1d010EaaC";//地址
        String contractAddress = "0xd08f65e3895750201c620b1951079e4ee12a251a";//代币地址
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
        String txid = "0xb4628a779ce00cb5dd0b321325554802f701600516fed8c292511c831151f06a";
        Boolean transferStatus = ethWalletService.getStatusByHash(txid);
        System.out.println(transferStatus);
    }

    /**
* 获取交易信息
* */
    @Test
    public void transactionInfo() throws IOException {
        String hash = "0xb4628a779ce00cb5dd0b321325554802f701600516fed8c292511c831151f06a";
        TransactionInfo transactionInfo = ethWalletService.transactionInfo(hash);
        System.out.println(JSON.toJSONString(transactionInfo));
    }


}
