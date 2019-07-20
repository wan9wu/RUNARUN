package org.elastos.dma.dmademo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.activity.wallet.WalletBackUpActivity;
import org.elastos.dma.dmademo.config.SystemConfig;
import org.elastos.dma.dmademo.tool.NativeDataService;
import org.elastos.dma.dmademo.tool.Waiter;

import java.util.Map;


/**
 * Created by xianxian on 2016/12/1.
 */
public class SettingActivity extends Activity implements View.OnClickListener {


    LinearLayout lin_commit, lin_alert_pwd_bg,topbar_back;
    TableRow  wallet_backup_row;
    TextView topbar_title,topbar_save,tv_did;
    EditText edt_wallet_pwd,edt_wallet_pwdnote;
    Button btn_pwd_cencel, btn_pwd_ensure;
    ImageView img_copy;
    String  words = "",errormsg="";
    private Activity mainActivity;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mainActivity=SettingActivity.this;
        topbar_title = findViewById(R.id.topbar_title);
        topbar_save =  findViewById(R.id.topbar_save);
        topbar_back = findViewById(R.id.topbar_back);

        lin_commit = findViewById(R.id.lin_commit);
        lin_alert_pwd_bg = findViewById(R.id.lin_alert_pwd_bg);

        wallet_backup_row = findViewById(R.id.tr_wallet_backup);
        btn_pwd_cencel = findViewById(R.id.btn_pwd_cencel);
        btn_pwd_ensure = findViewById(R.id.btn_pwd_ensure);
        edt_wallet_pwd = findViewById(R.id.edt_wallet_pwd);
        edt_wallet_pwdnote = findViewById(R.id.edt_wallet_pwdnote);
        tv_did = findViewById(R.id.tv_did);
        img_copy = findViewById(R.id.img_copy);

        wallet_backup_row.setOnClickListener(this);
        btn_pwd_cencel.setOnClickListener(this);
        btn_pwd_ensure.setOnClickListener(this);
        topbar_back.setOnClickListener(this);
        img_copy.setOnClickListener(this);
        topbar_save.setOnClickListener(this);

        GradientDrawable myGrad1 = (GradientDrawable) lin_alert_pwd_bg.getBackground();
        myGrad1.setColor(getResources().getColor(R.color.white));

        tv_did.setText(SystemConfig.did);


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    if(dialog!=null&&dialog.isShowing()){
                        dialog.dismiss();
                    }

                        Intent intent = new Intent(mainActivity, WalletBackUpActivity.class);
                        intent.putExtra("source", "setting");
                        intent.putExtra("words", words);
                        startActivity(intent);


                    break;
                case 1:
                    if(dialog==null){
                    dialog= Waiter.initProgressDialog(SettingActivity.this,getString(R.string.title_loading));
                }
                    dialog.show();
                    break;
                case 2:
                    if(errormsg!=null&&errormsg.length()>0){
                        Waiter.alertErrorMessage(errormsg, mainActivity);
                        errormsg="";
                    }

                    break;
                case 3:
//                    Waiter.alertErrorMessage(getString(R.string.delete_wallet_error),mainActivity);
                    break;

            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.topbar_back:
                finish();
                break;
            case R.id.tr_wallet_backup:
                lin_commit.setClickable(true); 
                lin_commit.setVisibility(View.VISIBLE);
               
                break;
        

            case R.id.btn_pwd_cencel:
                lin_commit.setVisibility(View.GONE);
                edt_wallet_pwd.setText("");
                break;
            case R.id.btn_pwd_ensure:
                String  pwd=edt_wallet_pwd.getText().toString();
                if (Waiter.CheckEditText(edt_wallet_pwd)) {
                    Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(this);
                    if(walletMap==null&&walletMap.get("privateKeyPwd")==null&&walletMap.get("privateKeyPwd").length()==0) {
                        return;
                    }

                    if(!pwd.equals(walletMap.get("privateKeyPwd").toString())){
                        Waiter.alertErrorMessage("密码错误",mainActivity);
                        return;
                    }
                    lin_commit.setVisibility(View.GONE);
                    commit();

                }
                break;

           
            case R.id.img_copy:
                ClipboardManager cm = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(SystemConfig.did);
                Waiter.alertErrorMessage(getString(R.string.copy_success),mainActivity);
                break;
          
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.equals(""))
            return;
        
    }
private void commit(){
    Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(mainActivity);
    if(walletMap==null&&walletMap.get("privateKeyPwd")==null&&walletMap.get("privateKeyPwd").length()==0) {
        return;
    }
    handler.sendEmptyMessage(1);
    exportMnemonic(walletMap);
    edt_wallet_pwd.setText("");
}
    public void exportMnemonic(final  Map<String,String> walletMap) {
         words = walletMap.get("mnemonic");
         handler.sendEmptyMessage(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
