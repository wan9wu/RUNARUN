package org.elastos.dma.dmademo;

import android.support.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.RandomStringUtils;
import org.elastos.dma.base.entity.eth.EthAccount;
import org.elastos.dma.service.assetManagement.ticket.AssetService;
import org.elastos.dma.service.assetManagement.ticket.impl.AssetServiceImpl;
import org.elastos.dma.utility.dto.AssetInfoDto;
import org.elastos.dma.utility.dto.ContractInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AssetTest {

    String node = "http://47.105.149.174:8545";
    AssetService assetManagementService = new AssetServiceImpl(node);

    BigInteger gasPrice = BigInteger.valueOf(1000000000);

    BigInteger gasLimit = BigInteger.valueOf(8000000);
    String privateKey = "d6ed30f07efa7ccd641186e752f827f9f4901c797d3b614e9f6aee56a031147f";//私钥


    /***
     * 创建资产合约
     */
    @Test
    public void testDeploy() throws Exception {

        System.out.println(deploy());//资产合约地址
        //0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce
    }

    private String deploy() throws Exception {
        String name = "Test Asset";//资产名称
        String symbol = "TA";//
        String _metadata = "TestData";
        String assetAddress = assetManagementService.deploy(privateKey, gasPrice, gasLimit, name, symbol, _metadata);
        return assetAddress;
    }


    /***
     * 创建资产
     */
    @Test
    public void testMint() throws Exception {
        String hash = mint(null);
        System.out.println(hash);
    }

    private String mint(String assetAddress) throws Exception {
        BigInteger tokenid = BigInteger.valueOf(1001);
        String to = new EthAccount(privateKey).getAddress();//资产拥有者
        String info = "testInfo";//资产信息
        String hash = assetManagementService.mint(assetAddress, privateKey, gasPrice, gasLimit, to, info, tokenid, true, true);
        return hash;
    }

    /**
     * 一键发布合约创建资产
     *
     * @throws Exception
     */
    @Test
    public void testDeployAndMint() throws Exception {
        String address = deploy();
        System.out.println("address = " + address);
        ;
        String mintHash = mint(address);

        getStatus(mintHash);

        // I/System.out: statusByHash = true
        // I/System.out: mintHash = 0x7595e557989c95688f6f6fba99df11d22ccad61737bdd7f5310e140b7c44010a
        // I/System.out: address = 0xe8991b49a4343dfe5443d24bdc58197d78536ffd
    }

    Boolean getStatus(String hash) {
        while (true) {
            try {
                Thread.sleep(1000L);
                Boolean statusByHash = assetManagementService.getStatusByHash(hash);
                if (statusByHash != null) {
                    System.out.println("statusByHash = " + statusByHash);
                    System.out.println("mintHash = " + hash);
                    return statusByHash;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 批量创建资产
     */
    @Test
    public void mintWithArray() throws Exception {
        String assetAddress = "0xe8991b49a4343dfe5443d24bdc58197d78536ffd";//资产合约地址
        List<BigInteger> list = new ArrayList<>();//资产id数组
        list.add(new BigInteger(RandomStringUtils.random(5, "1234567890")));
        list.add(new BigInteger(RandomStringUtils.random(5, "1234567890")));
        list.add(new BigInteger(RandomStringUtils.random(5, "1234567890")));
        list.add(new BigInteger(RandomStringUtils.random(5, "1234567890")));
        String to = new EthAccount(privateKey).getAddress();//资产拥有者
        String info = "testInfo";//资产信息
        String hash = assetManagementService.mintWithArray(assetAddress, privateKey, gasPrice, gasLimit, to, info, list, true, true);
        System.out.println(hash);

        //I/System.out: 0x47000cf89bda5cb138c313ed153bc0fa19f966e916de1cdb4f1113da90805935
    }


    /**
     * 转移资产
     */
    @Test
    public void transfer() throws Exception {
        String assetAddress = "0xe8991b49a4343dfe5443d24bdc58197d78536ffd";//资产合约地址
        BigInteger tokenid = BigInteger.valueOf(1001);//资产id
        String from = new EthAccount(privateKey).getAddress();//资产拥有者
        String to = "0xd07de12d15e686c013666fb9c47f23c5997faa21";//资产接收者
        String hash = assetManagementService.transfer(assetAddress, privateKey, gasPrice, gasLimit, from, to, tokenid);
        System.out.println(hash);//资产合约地址
        //I/System.out: 0x2abbf3a48412b7bac7c70c704f708a66c0bf438b5541938f2002337f987cf016

    }

    /**
     * 批量转移资产
     */
    @Test
    public void transferWithArray() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        String privateKey = "9fab7194e57f483c9bf27a8583c0abf4488a64529a12e8bfec4b76efbe66732a";//私钥
        List<BigInteger> list = new ArrayList<>();//资产id数组
        list.add(BigInteger.valueOf(1002));
        String from = "0xA258f950203b46A4036CE4c71BC64CE1d010EaaC";//资产拥有者
        String to = "0x21f017BA0937426e8d68417b59658e2DC74c8936";//资产接收者
        String hash = assetManagementService.transferWithArray(assetAddress, privateKey, gasPrice, gasLimit, from, to, list);
        System.out.println(hash);
    }


    /***
     * 资产授权
     */
    @Test
    public void approve() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        String privateKey = "f4b7957d7f11b014a77ad80e5d03d1c732f51c08ec7925c3d6175e19d099d832";//私钥
        BigInteger tokenid = BigInteger.valueOf(1003);//资产id
        String to = "0x21f017BA0937426e8d68417b59658e2DC74c8936";//授权地址者
        String hash = assetManagementService.approve(assetAddress, privateKey, gasPrice, gasLimit, to, tokenid);
        System.out.println(hash);
    }


    /***
     * 批量资产授权
     */
    @Test
    public void approveWithArray() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        String privateKey = "f4b7957d7f11b014a77ad80e5d03d1c732f51c08ec7925c3d6175e19d099d832";//私钥
        List<BigInteger> list = new ArrayList<>();//资产id数组
        list.add(BigInteger.valueOf(1002));
        String to = "0x21f017BA0937426e8d68417b59658e2DC74c8936";//授权地址者
        String hash = assetManagementService.approveWithArray(assetAddress, privateKey, gasPrice, gasLimit, to, list);
        System.out.println(hash);
    }


    /**
     * 获取授权地址
     */
    @Test
    public void getApproved() throws Exception {


        String assetAddress = "0x0f2ccde59b6acecd488784d8d96e04373b27f81d";//资产合约地址
        BigInteger tokenid = BigInteger.valueOf(8118531711548655L);
        String approvedAddress = assetManagementService.getApproved(assetAddress, tokenid);
        System.out.println(approvedAddress);
    }


    /**
     * 拥有资产数量
     */
    @Test
    public void balanceOf() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        String owner = "0x21f017BA0937426e8d68417b59658e2DC74c8936";
        BigInteger balanceOf = assetManagementService.balanceOf(assetAddress, owner);
        System.out.println(balanceOf);
    }


    /**
     * 拥有的资产id
     */
    @Test
    public void tokenIds() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        String owner = "0x21f017BA0937426e8d68417b59658e2DC74c8936";
        List<BigInteger> tokenIds = assetManagementService.tokenIds(assetAddress, owner);
        System.out.println(JSON.toJSONString(tokenIds));
    }


    /**
     * 资产信息
     */
    @Test
    public void assetInfo() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        BigInteger tokenid = BigInteger.valueOf(1002);
        AssetInfoDto assetInfoDto = assetManagementService.assetInfo(assetAddress, tokenid);
        System.out.println(JSON.toJSONString(assetInfoDto));
    }


    /**
     * 资产合约信息
     */
    @Test
    public void getContractInfo() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        ContractInfoDto contractInfoDto = assetManagementService.getContractInfo(assetAddress);
        System.out.println(JSON.toJSONString(contractInfoDto));
    }

    /**
     * 资产拥有者
     */
    @Test
    public void ownerOf() throws Exception {
        AssetServiceImpl assetManagementService = new AssetServiceImpl("http://47.105.149.174:8545");
        String contractAddress = "0x0be46e6e18b65b45f7caefa863ff715780a98f47";
        BigInteger tokenId = new BigInteger("864842349537751");
        String ownerOf = assetManagementService.ownerOf(contractAddress, tokenId);
        System.out.println("ownerOf = " + ownerOf);
        boolean tokenIsTransfer = assetManagementService.getCanTransfer(contractAddress, tokenId);
        System.out.println(tokenIsTransfer);
    }


    /**
     * 资产合约拥有者
     */
    @Test
    public void owner() throws Exception {
        String assetAddress = "0xe8699073280c2f8bf6a47a2dd3c2ba4dcabd22ce";//资产合约地址
        String owner = assetManagementService.owner(assetAddress);
        System.out.println(owner);
    }


    /***
     * 资产授权
     */
    @Test
    public void burn() throws Exception {

        //{"owner":"0xcef63da9905b6b2c74146a6cdbd7a7a533ebddee","privateKey":"1F1A4EA2BB3DBB1FA898B335B22EF2CFB5E3C9FA5D5672A61A83CA47264014C1","tokenId":7361349071931969,"contractAddress":"0x3d4a2c811d4b3d70ef97c05887a784fa95de5fe9"}
        //销毁tokenId 7361349071931973 , hash:0xed8945a533a6c6f159d0ccdb355ef1b7d93d955c5bf2436a5f46ef877cc94fb0

        String contractAddress = "0x3d4a2c811d4b3d70ef97c05887a784fa95de5fe9";//资产合约地址
        String privateKey = "1F1A4EA2BB3DBB1FA898B335B22EF2CFB5E3C9FA5D5672A61A83CA47264014C1";//私钥
        BigInteger tokenid = BigInteger.valueOf(7361349071931973L);//资产id

        String assetOwner = assetManagementService.ownerOf(contractAddress, tokenid);
        System.out.println("s = " + assetOwner);
        String owner = assetManagementService.owner(contractAddress);
        String hash = assetManagementService.burn(contractAddress, privateKey, gasPrice, gasLimit, assetOwner, tokenid);
        System.out.println(hash);


        Boolean falg = true;

        while
                (falg = true) {
            try {
                falg = assetManagementService.getStatusByHash(hash);
                System.out.println(falg);
            } catch (IOException e) {
            }
        }
    }


    @Test
    public void testQuery() {
        try {
            String has = "0x527a303f0ed54b011c1554fa7c5e70b2a9750e057ca62e47fe03d664539764d5,0x5048d703d3c1a05cce7f97686be04ac9e2a32cfa06110480520447578be1dadd";
            String[] split = has.split(",");
            Boolean transferStatus = assetManagementService.getStatusByHash(split[1]);
            Boolean transferStatus1 = assetManagementService.getStatusByHash(split[0]);
            System.out.println(transferStatus1 && transferStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBalance() throws Exception {
        AssetServiceImpl assetManagementService = new AssetServiceImpl("http://47.105.149.174:8545");
        BigInteger bigInteger = assetManagementService.balanceOf("0x69ec34bcd358d496793393ed73ca4192c9478c88", "0xcef63da9905b6b2c74146a6cdbd7a7a533ebddee");
        System.out.println("bigInteger = " + bigInteger);
    }

    @Test
    public void getApprove() {
        AssetServiceImpl assetManagementService = new AssetServiceImpl("http://47.105.149.174:8545");
        try {
            String approved = assetManagementService.getApproved("0x0be46e6e18b65b45f7caefa863ff715780a98f47", new BigInteger("864842349537759"));
            System.out.println("assetManagementService = " + approved);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
