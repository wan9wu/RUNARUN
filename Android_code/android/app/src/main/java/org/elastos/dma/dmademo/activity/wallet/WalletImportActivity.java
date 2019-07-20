package org.elastos.dma.dmademo.activity.wallet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.elastos.dma.base.entity.did.DIDAccount;
import org.elastos.dma.base.entity.ela.ElaAccount;
import org.elastos.dma.base.entity.eth.EthAccount;
import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.activity.HomeActivity;
import org.elastos.dma.dmademo.activity.LoginActivity;
import org.elastos.dma.dmademo.activity.MainActivity;
import org.elastos.dma.dmademo.config.SystemConfig;
import org.elastos.dma.dmademo.service.AccountService;
import org.elastos.dma.dmademo.tool.NativeDataService;
import org.elastos.dma.dmademo.tool.NodeClient;
import org.elastos.dma.dmademo.tool.Waiter;
import org.elastos.dma.service.utility.entity.Account;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xianxian on 2016/12/1.
 */
public class WalletImportActivity extends Activity implements View.OnClickListener {

    View topView;
    LinearLayout tab_word,tab_keystore,tab_privatekey,topbar_back,lin_words,lin_keystore,lin_privatekey,lin_words_pwd;
    TextView tv_wallet_import,tv_wallat_words;
    TextView topbar_title,title_words,title_words_,title_keystore,title_keystore_,title_privatekey,title_privatekey_;
    EditText edt_wallet_pwd,edt_wallet_repwd,edt_wallet_pwdnote,edt_wallet_privatekey,edt_wallet_words,edt_wallet_keystore;
    int action=0;
    private Dialog dialog;
    boolean ispost=false;
    private String errormsg="",linkType="";
    AccountService accountService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_import);
        accountService =NodeClient.getAccountClient();
         topbar_title=(TextView)findViewById(R.id.topbar_title);
        topbar_back=(LinearLayout)findViewById(R.id.topbar_back);
        tab_word=(LinearLayout)findViewById(R.id.tab_word);
        tab_keystore=(LinearLayout)findViewById(R.id.tab_keystore);
        tab_privatekey=(LinearLayout)findViewById(R.id.tab_privatekey);

        lin_words=(LinearLayout)findViewById(R.id.lin_words);
        lin_keystore=(LinearLayout)findViewById(R.id.lin_keystore);
        lin_privatekey=(LinearLayout)findViewById(R.id.lin_privatekey);
        lin_words_pwd=(LinearLayout)findViewById(R.id.lin_words_pwd);

        tv_wallet_import=(TextView)findViewById(R.id.tv_wallet_import);
        title_words=(TextView)findViewById(R.id.title_words);
        title_words_=(TextView)findViewById(R.id.title_words_);
        title_keystore=(TextView)findViewById(R.id.title_keystore);
        title_keystore_=(TextView)findViewById(R.id.title_keystore_);
        title_privatekey=(TextView)findViewById(R.id.title_privatekey);
        title_privatekey_=(TextView)findViewById(R.id.title_privatekey_);


        edt_wallet_pwd=(EditText)findViewById(R.id.edt_wallet_pwd);
        edt_wallet_repwd =(EditText)findViewById(R.id.edt_wallet_repwd);
        edt_wallet_pwdnote=(EditText)findViewById(R.id.edt_wallet_pwdnote);
        edt_wallet_privatekey=(EditText)findViewById(R.id.edt_wallet_privatekey);
        edt_wallet_words=(EditText)findViewById(R.id.edt_wallet_words);
        edt_wallet_keystore=(EditText)findViewById(R.id.edt_wallet_keystore);

        tab_word.setOnClickListener(this);
        tab_keystore.setOnClickListener(this);
        tab_privatekey.setOnClickListener(this);
        topbar_back.setOnClickListener(this);
        tv_wallet_import.setOnClickListener(this);
        topbar_title.setText(getString(R.string.import_wallet));
        linkType= SystemConfig.linkType.toLowerCase();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch(msg.what)
            {
                case 0:
                    if (dialog!=null&&dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if(LoginActivity.instance!=null){
                        LoginActivity.instance.finish();
                    }
                    if(!SystemConfig.haswallet&&SystemConfig.ethAddress!=null&&SystemConfig.ethAddress.length()>0){
                        Intent intent=new Intent(WalletImportActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    break;
                case 1:
                    dialog= Waiter.initProgressDialog(WalletImportActivity.this,getString(R.string.title_loading));
                    dialog.show();
                    break;
                case 2:
                    if (dialog!=null&&dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Waiter.alertErrorMessage(errormsg,WalletImportActivity.this);
                    if(errormsg.equals("钱包已存在，密码更新为新密码")){
                        finish();
                    }
                    break;
                case 3:
                    if (dialog!=null&&dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_wallet_import:
                if(!Waiter.CheckEditText(edt_wallet_pwd)){
                    return;
                     }

                if(action==0){
                    if(!edt_wallet_pwd.getText().toString().equals(edt_wallet_repwd.getText().toString())){
                        Waiter.alertErrorMessage(getString(R.string.password_not_match),getApplicationContext());
                        return;
                    }
                    if(edt_wallet_pwd.getText().toString().length()<8){
                        Waiter.alertErrorMessage(getString(R.string.password_not_length),getApplicationContext());
                        return;
                    }
                    if(!Waiter.CheckEditText(edt_wallet_words)){
                        return;
                    }
                    String[] words=edt_wallet_words.getText().toString().split(" ");
                    if(words.length!=12){
                        Waiter.alertErrorMessage(getString(R.string.input_mnemonic_error),getApplicationContext());
                        return;
                    }

                }else if(action==1){
                    if(!Waiter.CheckEditText(edt_wallet_keystore)){
                        return;
                    }
                    String keystore=edt_wallet_keystore.getText().toString();
                    if(!keystore.startsWith("{")&&!keystore.endsWith("}")){
                        Waiter.alertErrorMessage(getString(R.string.input_mnemonic_error),getApplicationContext());
                        return;
                    }
                }else if(action==2){
                    if(!edt_wallet_pwd.getText().toString().equals(edt_wallet_repwd.getText().toString())){
                        Waiter.alertErrorMessage(getString(R.string.password_not_match),getApplicationContext());
                        return;
                    }
                    if(edt_wallet_pwd.getText().toString().length()<8){
                        Waiter.alertErrorMessage(getString(R.string.password_not_length),getApplicationContext());
                        return;
                    }
                    if(!Waiter.CheckEditText(edt_wallet_privatekey)){
                        return;
                    }
                    String privatekey=edt_wallet_privatekey.getText().toString();
                    if(privatekey.contains(" ")||privatekey.contains("{")){
                        Waiter.alertErrorMessage(getString(R.string.input_mnemonic_error),getApplicationContext());
                        return;
                    }

                }
                createWallet(edt_wallet_pwd.getText().toString());
                break;
            case R.id.tab_word:
                action=0;
                resetTab();
                lin_words.setVisibility(View.VISIBLE);
                title_words.setTextColor(getResources().getColor(R.color.white));
                title_words_.setVisibility(View.VISIBLE);
                lin_words_pwd.setVisibility(View.VISIBLE);

                break;
            case R.id.tab_keystore:
                action=1;
                resetTab();
                lin_keystore.setVisibility(View.VISIBLE);
                title_keystore.setTextColor(getResources().getColor(R.color.white));
                title_keystore_.setVisibility(View.VISIBLE);
                edt_wallet_words.setText("");
                edt_wallet_privatekey.setText("");

                break;
            case R.id.tab_privatekey:
                action=2;
                resetTab();
                lin_privatekey.setVisibility(View.VISIBLE);
                title_privatekey.setTextColor(getResources().getColor(R.color.white));
                title_privatekey_.setVisibility(View.VISIBLE);
                lin_words_pwd.setVisibility(View.VISIBLE);
                edt_wallet_keystore.setText("");
                edt_wallet_words.setText("");
                break;
            case R.id.topbar_back:
                Intent intent;
                if(!SystemConfig.haswallet&&SystemConfig.address!=null&&SystemConfig.address.length()>0){
                    intent=new Intent(this,MainActivity.class);
                    startActivity(intent);

                }

                finish();
                break;

        }
    }
    public void resetTab(){
        lin_words.setVisibility(View.GONE);
        lin_keystore.setVisibility(View.GONE);
        lin_privatekey.setVisibility(View.GONE);
        lin_words_pwd.setVisibility(View.GONE);
        edt_wallet_pwd.setHint(getString(R.string.wallet_password));
        title_words.setTextColor(getResources().getColor(R.color.gray_666666));
        title_words_.setVisibility(View.GONE);
        title_keystore.setTextColor(getResources().getColor(R.color.gray_666666));
        title_keystore_.setVisibility(View.GONE);
        title_privatekey.setTextColor(getResources().getColor(R.color.gray_666666));
        title_privatekey_.setVisibility(View.GONE);

    }

    public void createWallet(final String pwd){
        final String mnemonic=edt_wallet_words.getText().toString();
        final String privateKey=edt_wallet_privatekey.getText().toString().trim().replaceAll("\r|\n", "");
        final String keyStore=edt_wallet_keystore.getText().toString().replaceAll("\r|\n", "");
        handler.sendEmptyMessage(1);
        if(ispost){
            return;
        }
        ispost=true;
        new AsyncTask<Void, Void,  Account >(){
            @Override
            protected  Account doInBackground(Void... params) {
//                Map<String, Object> map=new HashMap<String, Object>();
                Account returnJson=null;
                if(action==0){
                    returnJson= accountService.exportMultiWalletByMnemonic(mnemonic);
                }else if(action==1){
//                    method=SystemConfig.WALLET_IMPORT_KEYSTORE;
                }else if(action==2){
                    returnJson= accountService.exportMultiWalletByPrivateKey(privateKey);
                }
                return returnJson;

            }
            protected void onPostExecute( Account  returnJson) {
                ispost=false;
                errormsg="";
                handler.sendEmptyMessage(3);
                Map<String,String> walletMap=new HashMap<>();
                if(returnJson!=null&&returnJson.getDidAccount()!=null&&returnJson.getEthAccount()!=null){
                    DIDAccount didAccount= returnJson.getDidAccount();
                    EthAccount ethAccount= returnJson.getEthAccount();
                    ElaAccount elaAccount=returnJson.getElaAccount();
                    walletMap.put("publicKey",didAccount.getPublicKey());
                    walletMap.put("mnemonic",didAccount.getMnemonic());
                    walletMap.put("did",didAccount.getDid());
                    String ethAddress=ethAccount.getAddress();
                    String address=elaAccount.getAddress();
                    walletMap.put("ethAddress",ethAddress);
                    walletMap.put("address",address);
                    walletMap.put("privateKey",ethAccount.getPrivateKey());
                    walletMap.put("elaPrivateKey",didAccount.getPrivateKey());
                    walletMap.put("privateKeyPwd",pwd);
                    SystemConfig.address=address;
                    SystemConfig.ethAddress=ethAddress;
                    SystemConfig.privatekey=ethAccount.getPrivateKey();
                    SystemConfig.elaPrivatekey=elaAccount.getPrivateKey();
                    SystemConfig.did=didAccount.getDid();
                    NativeDataService.getInstance().saveWallet(WalletImportActivity.this,walletMap);
                    handler.sendEmptyMessage(0);
                }

            };
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }

}
