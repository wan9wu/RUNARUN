package org.elastos.dma.dmademo.activity.wallet;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.activity.CustomScanActivity;
import org.elastos.dma.dmademo.config.SystemConfig;
import org.elastos.dma.dmademo.entity.WalletInfo;
import org.elastos.dma.dmademo.tool.NativeDataService;
import org.elastos.dma.dmademo.tool.NodeClient;
import org.elastos.dma.dmademo.tool.Waiter;
import org.elastos.dma.service.wallet.ElaWalletService;
import org.elastos.dma.service.wallet.EthWalletService;
import org.elastos.dma.utility.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;


/**
 * Created by xianxian on 2019/7/20.
 */
public class WalletSendActivity extends AppCompatActivity implements View.OnClickListener {

    View topView;
    LinearLayout topbar_back,lin_next,lin_sure,lin_commit,lin_commit_back,lin_gasfee;
    ImageView wallet_send_sys,img_sure_back;
    TextView wallet_send_amount,wallet_send_current,wallet_send_next,wallet_gas_text;
    EditText wallet_send_to,wallet_send_value,wallet_send_remarks,wallet_send_gas,wallet_send_pwd;
    TextView tv_transfer_value,tv_gasfee,tv_gasfee_0,tv_fromaddress,tv_toaddress,wallet_send_sure,wallet_send_commit,topbar_title;
    SeekBar seekBar;
    private WalletInfo obj;
    private Dialog dialog;
    boolean ispost=false;
    private String errormsg="",toaddress="",old_tx_hash="",old_age="";
    private double maxprice=100,minprice=9.1,gas_price=0,gas=0.06,ethprice=0;
    private long LIMIT=60000;
    private EthWalletService ethWalletService;
    private ElaWalletService elaWalletService;
    private WalletSendActivity mContext;
    private int verifyCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet_send);
        mContext=WalletSendActivity.this;
        ethWalletService=NodeClient.getClient();
        elaWalletService=NodeClient.getElaServiceClient();
//        topbar_back=(LinearLayout)findViewById(R.id.topbar_back);
        wallet_send_sys=(ImageView) findViewById(R.id.img_sys);
        wallet_send_sys.setVisibility(View.VISIBLE);
//        topbar_title=findViewById(R.id.topbar_title);
//        topbar_title.setText(getString(R.string.wallet_send));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_sure_back=(ImageView) findViewById(R.id.img_sure_back);
        lin_commit_back=(LinearLayout) findViewById(R.id.lin_commit_back);
        wallet_send_amount=(TextView) findViewById(R.id.wallet_send_amount);
        wallet_send_pwd=(EditText) findViewById(R.id.wallet_send_pwd);
        wallet_send_current=(TextView) findViewById(R.id.wallet_send_current);
        wallet_send_next=(TextView) findViewById(R.id.wallet_send_next);
        wallet_send_sure=(TextView) findViewById(R.id.wallet_send_sure);
        wallet_send_commit=(TextView) findViewById(R.id.wallet_send_commit);
        wallet_gas_text=(TextView) findViewById(R.id.wallet_gas_text);
        tv_transfer_value=(TextView) findViewById(R.id.tv_transfer_value);
        tv_gasfee=(TextView) findViewById(R.id.tv_gasfee);
        tv_gasfee_0=(TextView) findViewById(R.id.tv_gasfee_0);
        tv_fromaddress=(TextView) findViewById(R.id.tv_fromaddress);
        tv_toaddress=(TextView) findViewById(R.id.tv_toaddress);
//        topbar_title=(TextView) findViewById(R.id.topbar_title);

        lin_next=(LinearLayout) findViewById(R.id.lin_next);
        lin_sure=(LinearLayout) findViewById(R.id.lin_sure);
        lin_commit=(LinearLayout) findViewById(R.id.lin_commit);
        lin_gasfee=(LinearLayout) findViewById(R.id.lin_gasfee);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        wallet_send_sys.setOnClickListener(this);
        wallet_send_amount.setOnClickListener(this);
        wallet_send_current.setOnClickListener(this);
        wallet_send_next.setOnClickListener(this);
        wallet_send_sure.setOnClickListener(this);
        wallet_send_commit.setOnClickListener(this);
//        topbar_back.setOnClickListener(this);
        lin_commit_back.setOnClickListener(this);
        img_sure_back.setOnClickListener(this);

        wallet_send_to=(EditText) findViewById(R.id.wallet_send_to);
        wallet_send_value=(EditText) findViewById(R.id.wallet_send_value);
        wallet_send_remarks=(EditText) findViewById(R.id.wallet_send_remarks);
        wallet_send_gas=(EditText) findViewById(R.id.wallet_send_gas);

        Intent intent=getIntent();
        toaddress=intent.getStringExtra("scanResult");
        if(toaddress!=null&&toaddress.length()>0){
            String[] sResult=toaddress.split("_");
            if(sResult.length==1){
                wallet_send_to.setText(sResult[0]);
            }else if(sResult.length==2){
                wallet_send_to.setText(sResult[0]);
                wallet_send_value.setText(sResult[1]);
            }

        }
        old_tx_hash=intent.getStringExtra("old_tx_hash");
        old_age=intent.getStringExtra("old_age");
        Bundle b=intent.getExtras();
        if(b.getSerializable("obj")!=null){
            obj= (WalletInfo) b.getSerializable("obj");
            if(obj!=null&&obj.getName()!=null){
                wallet_send_amount.setText(obj.getBalance());
                topbar_title.setText(obj.getName()+" "+getString(R.string.wallet_send));

            }
            if(obj.getName().equals("BTC")){
                wallet_gas_text.setText("0.0001BTC");
                wallet_send_gas.setText(getString(R.string.transfer_gas)+" "+"0.0001BTC");
                seekBar.setVisibility(View.GONE);
                lin_gasfee.setVisibility(View.GONE);

            }else if(obj.getName().equals("ELA")){
                wallet_gas_text.setText("0.000002ELA");
                wallet_send_gas.setText(getString(R.string.transfer_gas)+" "+"0.000002ELA");
                seekBar.setVisibility(View.GONE);
                lin_gasfee.setVisibility(View.GONE);
            }else{
//                getEthprice();
                wallet_gas_text.setText("0.0078");
                wallet_send_gas.setText(getString(R.string.transfer_gas)+" "+"0.0078");
                seekBar.setVisibility(View.GONE);
                lin_gasfee.setVisibility(View.GONE);
            }

        }else{
            wallet_send_amount.setText(obj.getEthbalance()+"ETH");

        }
        //seekbar设置监听
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*
            * seekbar改变时的事件监听处理
            * */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(SystemConfig.linkType.toUpperCase().equals("ETH")){
                    gas_price=minprice+(maxprice-minprice)*progress*0.01;
                    if(progress<=1){
                        gas_price=minprice;
                    }
                    gas=gas_price*LIMIT*Math.pow(10, -9);
                    wallet_gas_text.setText(String.format("%.6f",gas)+"ether");
                }

            }
            /*
            * 按住seekbar时的事件监听处理
            * */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            /*
            * 放开seekbar时的时间监听处理
            * */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch(msg.what)
            {
                case 0:
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    finish();
                    break;
                case 1:
                    dialog= Waiter.initProgressDialog(WalletSendActivity.this,getString(R.string.title_transfer_loading));
                    dialog.show();
                    break;
                case 2:
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    break;
                case 3:
                    gas=gas_price*LIMIT*Math.pow(10, -9);
//                    wallet_gas_text.setText(String.format("%.6f",gas)+"ether");
                    break;
            }
        }
    };


    private void getWalletTransactionInfo(){
        final String _from= SystemConfig.ethAddress;
        final String _to= wallet_send_to.getText().toString();
        final String remarks= wallet_send_remarks.getText().toString();
        final String token= obj.getTokenAddress();
        final String _value= wallet_send_value.getText().toString();

         if(obj.getName().equals("ELA")){
            if(!_to.startsWith("E")){
                Waiter.alertErrorMessage(getString(R.string.transfer_address_error),WalletSendActivity.this);
                return;
            }
        }else{
            if(_to.length()!=42||!_to.startsWith("0x")){
                Waiter.alertErrorMessage(getString(R.string.transfer_address_error),WalletSendActivity.this);
                return;
            }
        }

        double balanceValue=Double.parseDouble(obj.getBalance());
        double transferValue=Double.parseDouble(wallet_send_value.getText().toString());
        if(SystemConfig.linkType.toLowerCase().equals("eth")){
            double ethValue=SystemConfig.ethBalance;
            if(gas>ethValue){
                Waiter.alertErrorMessage(getString(R.string.transfer_gas_error),WalletSendActivity.this);
                return;
            }

            if(transferValue>balanceValue){
                Waiter.alertErrorMessage(getString(R.string.transfer_balance_error),WalletSendActivity.this);
                return;
            }
        }else{
            if(transferValue+gas>balanceValue){
                Waiter.alertErrorMessage(getString(R.string.transfer_balance_error),WalletSendActivity.this);
                return;
            }
        }

        if(ispost){
            return;
        }
        ispost=true;
        handler.sendEmptyMessage(1);
        //发起转账
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {


//                String _to
//                String _value
                BigInteger gasPrice=SystemConfig.gasPrice;
                BigInteger gasLimit=SystemConfig.gasLimit;
                String hash=null;
                try {

                   ;

                if(SystemConfig.linkType.toUpperCase().equals("ETH")){
                    String privateKey=SystemConfig.privatekey;
                    if(token.toUpperCase().equals("ETH")){
                        hash=ethWalletService.transfer(privateKey,_to,_value,gasPrice,gasLimit);
                    }
                }else{
                    String privateKey=SystemConfig.elaPrivatekey;
                    if(StringUtils.isEmpty(privateKey)){
                        //兼容老用户没有ELA私钥，使用的是一套私钥
                        privateKey=SystemConfig.privatekey;
                    }
                   hash= elaWalletService.transfer(privateKey,_to, new BigDecimal(_value),"");
                }
                } catch (Exception e) {
                e.printStackTrace();
                    handler.sendEmptyMessage(2);
            }
                return hash;
            }
            protected void onPostExecute(String returnJson) {

                if(returnJson!=null&&returnJson.length()>0){
                    handler.sendEmptyMessage(2);
                    NativeDataService.getInstance().saveTransferTime(getApplicationContext());
                    Waiter.alertErrorMessage(getString(R.string.commit_success),WalletSendActivity.this);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ispost=false;
                            handler.sendEmptyMessage(0);
                        }
                    }, 2000);//3秒后执行Runnable中的run方法

                }else{
                    ispost=false;
                    String msg=getString(R.string.commit_fail);

                    Waiter.alertErrorMessage(msg,WalletSendActivity.this);
                    handler.sendEmptyMessage(2);
                }
            };
        }.execute();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.equals(""))
            return;
        switch (requestCode) {

            case 49374:
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
                if(intentResult != null) {
                    if(intentResult.getContents() == null) {
                        Toast.makeText(this,getString(R.string.message_notnull),Toast.LENGTH_LONG).show();
                    } else {

                        // ScanResult 为 获取到的字符串
                        String scanResult = intentResult.getContents();
                        String[] sResult=scanResult.split("_");
                        if(sResult.length==1){
                            wallet_send_to.setText(sResult[0]);
                        }else if(sResult.length==2){
                            wallet_send_to.setText(sResult[0]);
                            wallet_send_value.setText(sResult[1]);
                        }


                    }
                }
                break;
            case 2:
             String address= data.getStringExtra("address");
                wallet_send_to.setText(address);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wallet_send_next:
                 String _to= wallet_send_to.getText().toString();
                 String _from= SystemConfig.ethAddress;
                if(obj.getName().equals("BTC")){
                    if(!_to.startsWith("1")){
                        Waiter.alertErrorMessage(getString(R.string.transfer_address_error),WalletSendActivity.this);
                        return;
                    }
                }else if(obj.getName().equals("ELA")){
                    if(!_to.startsWith("E")){
                        Waiter.alertErrorMessage(getString(R.string.transfer_address_error),WalletSendActivity.this);
                        return;
                    }
                }else{
                    if(_to.length()!=42||!_to.startsWith("0x")){
                        Waiter.alertErrorMessage(getString(R.string.transfer_address_error),WalletSendActivity.this);
                        return;
                    }
                }
                if(!Waiter.CheckEditText(wallet_send_value)){
                    return;
                }
                if(_from.equals(_to)){
                    Waiter.alertErrorMessage(getString(R.string.transfer_address_equals),WalletSendActivity.this);
                    return;
                }
                double balanceValue=Double.parseDouble(obj.getBalance());
                double transferValue=Double.parseDouble(wallet_send_value.getText().toString());
                if(SystemConfig.linkType.toLowerCase().equals("eth")){
                    double ethValue=SystemConfig.ethBalance;
                    if(gas>ethValue){
                        Waiter.alertErrorMessage(getString(R.string.transfer_gas_error),WalletSendActivity.this);
                        return;
                    }

                    if(transferValue>balanceValue){
                        Waiter.alertErrorMessage(getString(R.string.transfer_balance_error),WalletSendActivity.this);
                        return;
                    }

                }else{
                    if(transferValue+gas>balanceValue){
                        Waiter.alertErrorMessage(getString(R.string.transfer_balance_error),WalletSendActivity.this);
                        return;
                    }
                    tv_gasfee_0.setText(tv_gasfee.getText().toString());
                }


                lin_next.setVisibility(View.VISIBLE);
                lin_sure.setVisibility(View.VISIBLE);
                lin_commit.setVisibility(View.GONE);
                tv_transfer_value.setText(wallet_send_value.getText().toString()+" "+obj.getName());
                tv_gasfee.setText(wallet_gas_text.getText().toString());
                if(obj.getName().equals("ELA")){
                    tv_fromaddress.setText(SystemConfig.address);
                }else{
                    tv_fromaddress.setText(SystemConfig.ethAddress);
                }

                tv_toaddress.setText(wallet_send_to.getText());
                break;
            case R.id.wallet_send_sure:
                lin_sure.setVisibility(View.GONE);
                lin_commit.setClickable(true);

                    lin_commit.setVisibility(View.VISIBLE);
                break;
            case R.id.wallet_send_commit:
                String pwd=wallet_send_pwd.getText().toString();
                Map<String,String> map=NativeDataService.getInstance().loadWallet(WalletSendActivity.this);
                if(map.get("privateKeyPwd")!=null&&map.get("privateKeyPwd").length()>0){
                    if(!pwd.equals(map.get("privateKeyPwd"))){
                        Waiter.alertErrorMessage(getString(R.string.wallet_password_error),WalletSendActivity.this);
                        return;
                    }
                }

                if(!Waiter.CheckEditText(wallet_send_pwd)){
                    return;
                }

                    getWalletTransactionInfo();


                break;
            case R.id.img_sure_back:
                lin_next.setVisibility(View.GONE);
                break;
            case R.id.lin_commit_back:
                lin_sure.setVisibility(View.VISIBLE);
                lin_commit.setVisibility(View.GONE);
                break;

            case R.id.topbar_back:
                finish();
                break;

            case R.id.img_sys:
                IntentIntegrator intentIntegrator = new IntentIntegrator(WalletSendActivity.this);
                intentIntegrator
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                        .setPrompt(getString(R.string.title_sys_qrcode))//写那句提示的话
                        .setOrientationLocked(false)//扫描方向固定
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
