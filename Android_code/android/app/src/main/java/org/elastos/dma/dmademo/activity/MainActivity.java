package org.elastos.dma.dmademo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.service.merchant.ETHMerchantService;
import org.elastos.dma.service.merchant.impl.ETHMerchantServiceImpl;
import org.elastos.dma.service.wallet.ElaWalletService;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    private String elaNodeurl = "http://54.64.220.165:21334";//亦来云测试节点地址
//    private String elaNodeurl = "https://node-mainnet-restful.elastos.org";//亦来云测试节点地址
    String nodeurl = "http://192.168.31.102:8545";//ETH节点地址
    private ElaWalletService elaWalletService= new ElaWalletService(elaNodeurl);
    ETHMerchantService ethMerchantService = new ETHMerchantServiceImpl(nodeurl);
    private BigInteger gasPrice = BigInteger.valueOf(1000000000);
    private BigInteger gasLimit = BigInteger.valueOf(6000000);

    ImageView img_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void wallet(View v){
        Intent intent=new Intent(this,WalletIndexActivity.class);
        startActivity(intent);
    }

    public void setting(View v){
        Intent intent=new Intent(this,SettingActivity.class);
        startActivity(intent);
    }


}
