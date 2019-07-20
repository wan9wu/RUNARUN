package org.elastos.dma.dmademo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.elastos.dma.base.entity.did.DIDAccount;
import org.elastos.dma.base.entity.eth.EthAccount;
import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.config.SystemConfig;
import org.elastos.dma.dmademo.service.AccountService;
import org.elastos.dma.dmademo.tool.NativeDataService;
import org.elastos.dma.dmademo.tool.NodeClient;
import org.elastos.dma.dmademo.tool.PermisionUtils;
import org.elastos.dma.dmademo.tool.Waiter;
import org.elastos.dma.dmademo.activity.wallet.WalletBackUpActivity;
import org.elastos.dma.dmademo.activity.wallet.WalletImportActivity;
import org.elastos.dma.service.utility.entity.Account;

import java.util.Map;


public class LoginActivity extends Activity implements View.OnClickListener{
    EditText edt_password,edt_password_re;
    EditText edt_userName;
    TextView tv_import,tv_did,btn_login;
    public static LoginActivity instance;
    private Dialog dialog;
    private boolean ispost=false;
    AccountService accountService;
    String words="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=LoginActivity.this;
        setContentView(R.layout.activity_login);
        PermisionUtils.showContacts(instance);
        edt_password=findViewById(R.id.edt_password);
        edt_userName=findViewById(R.id.edt_userName);
        edt_password_re=findViewById(R.id.edt_password_re);
        btn_login=findViewById(R.id.btn_login);
        tv_import=findViewById(R.id.tv_import);
        tv_did=findViewById(R.id.tv_did);
        TextView tv_login_title=findViewById(R.id.tv_login_title);


        btn_login.setOnClickListener(this);
        tv_import.setOnClickListener(this);
        accountService =NodeClient.getAccountClient();
        Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(this);
        if(walletMap!=null&&walletMap.get("did")!=null&&walletMap.get("did").length()>0) {
            SystemConfig.did=walletMap.get("did");

            String walletname=walletMap.get("walletname");
             tv_did.setText(walletname);
            tv_did.setGravity(Gravity.CENTER);
            tv_did.setTextSize(18);
            btn_login.setText(getString(R.string.title_login));
            tv_login_title.setText(getString(R.string.title_login));
            tv_login_title.setVisibility(View.GONE);
            edt_userName.setText(walletMap.get("walletname"));
            edt_userName.setVisibility(View.GONE);
            edt_password_re.setVisibility(View.GONE);

        }else{
            btn_login.setText(getString(R.string.title_create_did));
            tv_login_title.setText(getString(R.string.button_create));
            edt_userName.setVisibility(View.VISIBLE);
            edt_password_re.setVisibility(View.VISIBLE);
        }
        PermisionUtils.showContacts(this);
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
                    break;
                case 1:
                    dialog= Waiter.initProgressDialog(LoginActivity.this,getString(R.string.title_loading));
                    dialog.show();
                    break;
                case 2:
                {
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
                case 3:
                {
                    Intent intent=new Intent(LoginActivity.this,WalletBackUpActivity.class);
                    intent.putExtra("words",words);
                    startActivity(intent);
                    finish();
                }
                break;
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:

                if(!Waiter.CheckEditText(edt_password)){
                    return;
                }

                if(edt_userName.getVisibility()==View.VISIBLE){
                    if(!Waiter.CheckEditText(edt_userName)){
                    return;
                }

                    NativeDataService.getInstance().saveWalletNmae(getApplicationContext(),edt_userName.getText().toString());

                }
                String password=edt_password.getText().toString();
                if(password.length()<8){
                    Waiter.alertErrorMessage(getString(R.string.wallet_password_error),LoginActivity.this);
                    return;
                }
                if(edt_password_re.getVisibility()==View.VISIBLE){
                    String password_re=edt_password_re.getText().toString();
                    if(!password.equals(password_re)){
                        Waiter.alertErrorMessage(getString(R.string.password_not_match),LoginActivity.this);
                        return;
                    }
                }

                verfryWallet(password);

                break;
            case R.id.tv_import:
            {
//                testCarrier();
//                testWallet();
                Intent intent=new Intent(LoginActivity.this,WalletImportActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
    private void verfryWallet(String pwd){
        Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(this);
        if(walletMap!=null&&walletMap.get("privateKeyPwd")!=null&&walletMap.get("privateKeyPwd").length()>0){
            if(!pwd.equals(walletMap.get("privateKeyPwd"))){
                Waiter.alertErrorMessage(getString(R.string.wallet_password_error),LoginActivity.this);
                return;
            }
            SystemConfig.ethAddress=walletMap.get("ethAddress");
            SystemConfig.address=walletMap.get("address");
            SystemConfig.privatekey=walletMap.get("privateKey");
            SystemConfig.elaPrivatekey=walletMap.get("elaPrivateKey");
            handler.sendEmptyMessage(2);

        }else{
            handler.sendEmptyMessage(1);
            if(ispost){
                return;
            }
            ispost=true;
            new AsyncTask<Void, Void,  Account >(){
                @Override
                protected  Account doInBackground(Void... params) {
                    Account returnJson= accountService.create();
                    return returnJson;
                }
                protected void onPostExecute( Account  returnJson) {
                    ispost=false;
                    handler.sendEmptyMessage(0);
                    if(returnJson!=null&&returnJson.getDidAccount()!=null&&returnJson.getEthAccount()!=null){
                        DIDAccount didAccount= (DIDAccount ) returnJson.getDidAccount();
                        EthAccount ethAccount=(EthAccount) returnJson.getEthAccount();
                        walletMap.put("publicKey",didAccount.getPublicKey());
                        walletMap.put("mnemonic",didAccount.getMnemonic());
                        walletMap.put("did",didAccount.getDid());
                        SystemConfig.did=didAccount.getDid();
                        String ethAddress=ethAccount.getAddress();
                        String address=didAccount.getAddress();
                        walletMap.put("ethAddress",ethAddress);
                        walletMap.put("address",address);
                        walletMap.put("privateKey",ethAccount.getPrivateKey());
                        String privateKey=didAccount.getPrivateKey();

                        walletMap.put("elaPrivateKey",privateKey);
                        walletMap.put("privateKeyPwd",pwd);
                        SystemConfig.address=address;
                        SystemConfig.ethAddress=ethAddress;
                        SystemConfig.elaPrivatekey=privateKey;
                        SystemConfig.privatekey=ethAccount.getPrivateKey();
                        words=didAccount.getMnemonic();
                        NativeDataService.getInstance().saveWallet(LoginActivity.this,walletMap);
                        handler.sendEmptyMessage(3);
                    }

                };
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200://刚才的识别码
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){//用户同意权限,执行我们的操作

                }else{//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(LoginActivity.this,"未开启定位权限,请手动到设置去开启权限",Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
