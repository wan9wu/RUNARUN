package org.elastos.dma.dmademo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.elastos.dma.base.entity.did.DIDAccount;
import org.elastos.dma.base.entity.ela.ElaAccount;
import org.elastos.dma.base.util.HdSupport;
import org.elastos.dma.service.passport.impl.PassportServiceImpl;
import org.elastos.dma.service.wallet.ElaWalletService;
import org.elastos.dma.utility.entity.ela.ReturnMsgEntity;
import org.elastos.dma.utility.entity.ela.SignDataEntity;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PassportTest {

    private String nodeurl = "http://54.64.220.165:21604";//亦来云测试节点地址
    private PassportServiceImpl passportService = new PassportServiceImpl(nodeurl);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.elastos.dma.dmademo", appContext.getPackageName());
    }




    /**
     * 创建did
     *
     * */
    @Test
    public void createDid() {
        DIDAccount didAccount = passportService.create(HdSupport.MnemonicType.ENGLISH);
        String didAddress = didAccount.getDid();//did地址
        String address = didAccount.getAddress();//侧链地址
        String mnemonic = didAccount.getMnemonic();//助记词
        String privateKey = didAccount.getPrivateKey();//私钥
        String publicKey = didAccount.getPublicKey();//公钥
        System.out.println(didAddress);
        System.out.println(address);
        System.out.println(mnemonic);
        System.out.println(privateKey);
        System.out.println(publicKey);

        //I/System.out: iZLKospEwpSXrXq9a3XMMLFBBvpAGHAm4r
        //I/System.out: ELzQZuHz7TfcJ4hyxRFivESu6wz916MnDB
        //I/System.out: urge various arch multiply lobster execute nest major decrease bubble design cluster
        //I/System.out: 6B27C800A181B5C139BD8E218C0C899E1D975541A89CC288EC3B5B64FCCC21D6
        //I/System.out: 028F664ABA6E4A8862FB18B04DE821AC91F9DEEE24B025896ED6801603B2ECB7B2
    }

    /*
     * 根据私钥导出钱包
     * */
    @Test
    public void exportByPrivateKey() {
        String privateKey = "6B27C800A181B5C139BD8E218C0C899E1D975541A89CC288EC3B5B64FCCC21D6";
        DIDAccount didAccount = passportService.exportByPrivateKey(privateKey);
        String did = didAccount.getDid();//did
        String address = didAccount.getAddress();//侧链地址
        String publicKey = didAccount.getPublicKey();//公钥
        System.out.println(did);
        System.out.println(address);
        System.out.println(privateKey);
        System.out.println(publicKey);

        //I/System.out: iZLKospEwpSXrXq9a3XMMLFBBvpAGHAm4r
        //I/System.out: ELzQZuHz7TfcJ4hyxRFivESu6wz916MnDB
        //I/System.out: 6B27C800A181B5C139BD8E218C0C899E1D975541A89CC288EC3B5B64FCCC21D6
        //I/System.out: 028F664ABA6E4A8862FB18B04DE821AC91F9DEEE24B025896ED6801603B2ECB7B2
    }

    /**
     * 查询DID侧链钱包余额
     */
    @Test
    public void testBalance(){
        ElaAccount b6873904CB64F13395F37D238F7F6580308F30C9E9ECB7EB98454A565A573B44 = new ElaAccount("B6873904CB64F13395F37D238F7F6580308F30C9E9ECB7EB98454A565A573B44");
        String balance = new ElaWalletService(nodeurl).balance(b6873904CB64F13395F37D238F7F6580308F30C9E9ECB7EB98454A565A573B44.getAddress());
        System.out.println("balance = " + balance);
    }
    /**
     * 设置DIDinfo
     * 传入map进行存储，如果遇到图片，先将图片上传到IPFS，再将hash存到DID
     */
    @Test
    public void testSetDidInfo(){
        ReturnMsgEntity<String> stringReturnMsgEntity = passportService.setDidInfo("B6873904CB64F13395F37D238F7F6580308F30C9E9ECB7EB98454A565A573B44", "B6873904CB64F13395F37D238F7F6580308F30C9E9ECB7EB98454A565A573B44", JSONObject.parseObject("{name:'tom',age:10}", new TypeReference<Map<String, String>>() {
        }));
        System.out.println("stringReturnMsgEntity = " + stringReturnMsgEntity);
    }

    /*
     * 根据 助记词导出钱包
     * */
    @Test
    public void exportByMnemonic() {
        String mnemonic = "urge various arch multiply lobster execute nest major decrease bubble design cluster";
        DIDAccount didAccount = passportService.exportByMnemonic(mnemonic);
        String did = didAccount.getDid();//did
        String address = didAccount.getAddress();//侧链地址
        String privateKey = didAccount.getPrivateKey();//私钥
        String publicKey = didAccount.getPublicKey();//公钥
        System.out.println(did);
        System.out.println(address);
        System.out.println(mnemonic);
        System.out.println(privateKey);
        System.out.println(publicKey);

        //I/System.out: iZLKospEwpSXrXq9a3XMMLFBBvpAGHAm4r
        //I/System.out: ELzQZuHz7TfcJ4hyxRFivESu6wz916MnDB
        //I/System.out: urge various arch multiply lobster execute nest major decrease bubble design cluster
        //I/System.out: 6B27C800A181B5C139BD8E218C0C899E1D975541A89CC288EC3B5B64FCCC21D6
        //I/System.out: 028F664ABA6E4A8862FB18B04DE821AC91F9DEEE24B025896ED6801603B2ECB7B2
    }

    @Test
    public void sign() throws Exception {
        String privateKey = "6B27C800A181B5C139BD8E218C0C899E1D975541A89CC288EC3B5B64FCCC21D6";
        String msg="test msg";
        SignDataEntity signDataEntity = passportService.sign(msg,privateKey);
        System.out.println(JSON.toJSONString(signDataEntity));
        //I/System.out: {"msg":"74657374206D7367","pub":"028F664ABA6E4A8862FB18B04DE821AC91F9DEEE24B025896ED6801603B2ECB7B2","sig":"FCF96E662E178A3DCBDFDE8A82AD069498DD756B6BA207D3B7A7ECED7A75666E16169ABD5E7D28FE9DE30D3F97E85BA3AF2F97810BCDA02C8295B888044D0CE5"}

    }

    @Test
    public void verify() throws Exception {
        String msg = "74657374206D7367";
        String sig = "FCF96E662E178A3DCBDFDE8A82AD069498DD756B6BA207D3B7A7ECED7A75666E16169ABD5E7D28FE9DE30D3F97E85BA3AF2F97810BCDA02C8295B888044D0CE5";
        String pub = "028F664ABA6E4A8862FB18B04DE821AC91F9DEEE24B025896ED6801603B2ECB7B2";
        Boolean aBoolean = passportService.verify(msg, sig, pub);
        System.out.println(aBoolean);
    }


}
