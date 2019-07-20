package org.elastos.dma.dmademo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hyper.messenger.app.service.ElastosService;
import com.hyper.messenger.app.service.GlobalApplication;

import org.elastos.dma.dmademo.R;


public class IndexActivity extends Activity {
    @Override
    protected void onStart(){
        super.onStart();
        Intent serviceIntent=new Intent(this, ElastosService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        GlobalApplication.setAppActive(true);



        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }


}
