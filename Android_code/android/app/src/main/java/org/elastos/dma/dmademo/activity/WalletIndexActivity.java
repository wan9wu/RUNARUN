package org.elastos.dma.dmademo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.config.SystemConfig;
import org.elastos.dma.dmademo.entity.WalletInfo;
import org.elastos.dma.dmademo.tool.NodeClient;
import org.elastos.dma.dmademo.tool.Waiter;
import org.elastos.dma.dmademo.activity.wallet.WalletAddressActivity;
import org.elastos.dma.dmademo.activity.wallet.WalletSendActivity;
import org.elastos.dma.service.wallet.ElaWalletService;
import org.elastos.dma.service.wallet.EthWalletService;

import java.math.BigDecimal;

/**
 * 钱包首页
 */

public class WalletIndexActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout topbar_back,lin_wallet_ela,lin_wallet_dma;
    private TextView tv_wallet_address,tv_wallet_address_did,tv_wallet_address_ela,tv_ela_blance,tv_did_balance, tv_eth_blance;
    ImageView img_copy_eth,img_copy_ela,img_copy_did;
    SwipeRefreshLayout swipe_refresh;
    private Activity mainActivity;
    private ElaWalletService elaWalletService;
    private EthWalletService ethWalletService;
    Handler handler;
    String elabalance="";
    private boolean isPost=false;
    Dialog dialog;
    WalletInfo obj;
    private Toolbar mToolbar;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(0xFF002BB5);
        mToolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mainActivity = WalletIndexActivity.this;

        tv_eth_blance =findViewById(R.id.tv_dma_blance);

        tv_wallet_address=findViewById(R.id.tv_wallet_address);
        tv_wallet_address_did=findViewById(R.id.tv_wallet_address_did);
        tv_wallet_address_ela=findViewById(R.id.tv_wallet_address_ela);
        tv_ela_blance=findViewById(R.id.tv_ela_blance);
        tv_did_balance=findViewById(R.id.tv_did_balance);
        tv_eth_blance =findViewById(R.id.tv_dma_blance);
        img_copy_eth=findViewById(R.id.img_copy_eth);
        img_copy_ela=findViewById(R.id.img_copy_ela);
        img_copy_did=findViewById(R.id.img_copy_did);
        img_copy_eth.setOnClickListener(this);
        img_copy_ela.setOnClickListener(this);
        tv_wallet_address.setText(SystemConfig.ethAddress);
        tv_wallet_address_ela.setText(SystemConfig.address);
        tv_wallet_address_did.setText(SystemConfig.did);
        tv_ela_blance.setText(SystemConfig.ELABalance+"");
        tv_eth_blance.setText( SystemConfig.ethBalance+"");
        tv_did_balance.setText( SystemConfig.didBalance+"");


        lin_wallet_ela =findViewById(R.id.lin_wallet_ela) ;
        lin_wallet_dma = findViewById(R.id.lin_wallet_dma) ;
        lin_wallet_ela.setOnClickListener(this);
        lin_wallet_dma.setOnClickListener(this);
        elaWalletService=NodeClient.getElaServiceClient();
        ethWalletService=NodeClient.getClient();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch(msg.what)
                {
                    case 0:{
                        BigDecimal bigDecimal=new BigDecimal(SystemConfig.ethBalance);
                        tv_eth_blance.setText(bigDecimal.setScale(6,BigDecimal.ROUND_DOWN).toString());
                    }

                    break;
                    case 1:{
                        BigDecimal bigDecimal=new BigDecimal(elabalance);
                        tv_ela_blance.setText( bigDecimal.setScale(6,BigDecimal.ROUND_DOWN).toString());
                    }

                    break;

                    case 2:{
                        BigDecimal bigDecimal=new BigDecimal(SystemConfig.didBalance);
                        tv_did_balance.setText( bigDecimal.setScale(6,BigDecimal.ROUND_DOWN).toString());
                    }

                    break;

                    case 3:

                        break;
                    case 4:
                        dialog= Waiter.initProgressDialog(mainActivity,getString(R.string.title_loading));
                        dialog.show();
                        break;

                }
            }
        };
        getElaBalance();
        getEthBalance();
        getDIDBalance();
    }
    private void getEthBalance(){
        //查询DMA和ETH账户余额
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    String jsonObject= ethWalletService.balance(SystemConfig.ethAddress);
                    if(jsonObject!=null){
                        SystemConfig.ethBalance=Double.parseDouble(jsonObject);
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            protected void onPostExecute(Integer result) {

            }

            ;
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    String jsonObject= ethWalletService.balance(SystemConfig.ethAddress);
                    if(jsonObject!=null){
                        SystemConfig.ethBalance=Double.parseDouble(jsonObject);
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            protected void onPostExecute(Integer result) {

            }

            ;
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private void getElaBalance(){
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    String jsonResult = elaWalletService.balance(SystemConfig.address);
                    if(jsonResult!=null){
                        SystemConfig.ELABalance=Double.parseDouble(jsonResult);
                        elabalance=jsonResult;
                        handler.sendEmptyMessage(1);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }

            protected void onPostExecute(Integer result) {

            }

            ;
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private void getDIDBalance(){
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    ElaWalletService didWalletService=new ElaWalletService(NodeClient.didip);
                    String jsonResult = didWalletService.balance(SystemConfig.address);
                    if(jsonResult!=null){
                        SystemConfig.didBalance=Double.parseDouble(jsonResult);
                        elabalance=jsonResult;
                        handler.sendEmptyMessage(1);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }

            protected void onPostExecute(Integer result) {

            }

            ;
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_wallet_address:
            {
                Intent intent=new Intent(mainActivity,WalletAddressActivity.class);
                intent.putExtra("address",SystemConfig.ethAddress);
                SystemConfig.linkType="ETH";
                mainActivity.startActivity(intent);

            }
            break;
            case R.id.tv_wallet_address_ela:
            {
                Intent intent=new Intent(mainActivity,WalletAddressActivity.class);
                intent.putExtra("address",SystemConfig.address);
                SystemConfig.linkType="ELA";
                mainActivity.startActivity(intent);


            }
            break;
            case R.id.tv_wallet_address_did:
            {
                Intent intent=new Intent(mainActivity,WalletAddressActivity.class);
                intent.putExtra("address",SystemConfig.did);
                SystemConfig.linkType="DID";
                mainActivity.startActivity(intent);


            }
            break;

            case R.id.img_copy_eth:
            {
                ClipboardManager cm = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(SystemConfig.ethAddress);
                Waiter.alertErrorMessage(getString(R.string.copy_success),mainActivity);
            }
            break;
            case R.id.img_copy_ela:
            {
                ClipboardManager cm = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(SystemConfig.address);
                Waiter.alertErrorMessage(getString(R.string.copy_success),mainActivity);
            }
            break;
            case R.id.img_copy_did:
            {
                ClipboardManager cm = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(SystemConfig.did);
                Waiter.alertErrorMessage(getString(R.string.copy_success),mainActivity);
            }
            break;
            case R.id.lin_wallet_dma:
            {
                WalletInfo walletInfo=new WalletInfo();
                walletInfo.setName("ETH");
                walletInfo.setBalance(SystemConfig.ethBalance+"");
                walletInfo.setTokenAddress("ETH");
                walletInfo.setWalletAddress(SystemConfig.ethAddress);
                SystemConfig.linkType="ETH";
                Intent intent=new Intent(this, WalletSendActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("obj", walletInfo);
                intent.putExtras(b);
                startActivity(intent);
            }
            break;
            case R.id.lin_wallet_ela:
            {
                WalletInfo walletInfo=new WalletInfo();
                walletInfo.setName("ELA");
                walletInfo.setBalance(SystemConfig.ELABalance+"");
                walletInfo.setTokenAddress("ELA");
                walletInfo.setWalletAddress(SystemConfig.address);
                SystemConfig.linkType="ELA";
                Intent intent=new Intent(this, WalletSendActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("obj", walletInfo);
                intent.putExtras(b);
                startActivity(intent);
            }
            break;
            case R.id.lin_wallet_did:
            {
                WalletInfo walletInfo=new WalletInfo();
                walletInfo.setName("DID");
                walletInfo.setBalance(SystemConfig.ELABalance+"");
                walletInfo.setTokenAddress("DID");
                walletInfo.setWalletAddress(SystemConfig.address);
                SystemConfig.linkType="DID";
                Intent intent=new Intent(this, WalletSendActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("obj", walletInfo);
                intent.putExtras(b);
                startActivity(intent);
            }
            break;
        }


    }

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WalletIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.setTitle("钱包");
    }
}
