package org.elastos.dma.dmademo.activity.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.activity.MainActivity;
import org.elastos.dma.dmademo.config.SystemConfig;


/**
 * Created by 备份钱包提示
 */
public class WalletBackUpActivity extends Activity implements View.OnClickListener {

    View topView;
    LinearLayout topbar_back,lin_wallet_backup,lin_wallet_words;

    TextView tv_wallat_backup,topbar_title,tv_wallat_words,tv_wallat_backup_title,tv_wallat_backup_content,tv_wallet_backupdesc;


    String address="",words="",source="";
    private int walletaction=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_backup);
        Intent intent=getIntent();
        words=intent.getStringExtra("words");
        source=intent.getStringExtra("source");
        walletaction=intent.getIntExtra("action",0);

        topbar_back=(LinearLayout)findViewById(R.id.topbar_back);
        topbar_title=findViewById(R.id.topbar_title);
        lin_wallet_backup=(LinearLayout)findViewById(R.id.lin_wallet_backup);
        lin_wallet_words=(LinearLayout)findViewById(R.id.lin_wallet_words);

        tv_wallat_backup=(TextView)findViewById(R.id.tv_wallat_backup);
        tv_wallat_words=(TextView)findViewById(R.id.tv_wallat_words);
        tv_wallat_backup_title=(TextView)findViewById(R.id.tv_wallat_backup_title);
        tv_wallat_backup_content=(TextView)findViewById(R.id.tv_wallat_backup_content);
        tv_wallet_backupdesc=(TextView)findViewById(R.id.tv_wallet_backupdesc);
        tv_wallat_words.setText(words);
        topbar_back.setOnClickListener(this);
        tv_wallat_backup.setOnClickListener(this);
        setContent();
    }
    private void setContent(){
        if(walletaction>2){

            lin_wallet_backup.setVisibility(View.GONE);
            lin_wallet_words.setVisibility(View.VISIBLE);
            topbar_title.setText(getString(R.string.backup_wallet));
            tv_wallat_words.setText(words);
            if(walletaction==3){
                tv_wallat_backup_title.setText(getString(R.string.copy_your_privatekey));
                tv_wallat_backup_content.setText(getString(R.string.privatekey_important));
            }else if(walletaction==4){
                tv_wallat_backup_title.setText(getString(R.string.copy_your_keystore));
                tv_wallat_backup_content.setText(Html.fromHtml(getString(R.string.keystore_important)));
            }

        }else {
            if(source!=null&&source.equals("setting")){
                tv_wallet_backupdesc.setVisibility(View.INVISIBLE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_wallat_backup:
                lin_wallet_backup.setVisibility(View.GONE);
                lin_wallet_words.setVisibility(View.VISIBLE);
                topbar_title.setText(getString(R.string.backup_mnemonic));
                topbar_back.setVisibility(View.VISIBLE);
                break;
            case R.id.topbar_back:
          Intent intent;
                if(!SystemConfig.haswallet&&words!=null&&words.length()>0){
                    intent=new Intent(this,MainActivity.class);
                    startActivity(intent);
                }
                finish();

                break;
        }
    }

}
