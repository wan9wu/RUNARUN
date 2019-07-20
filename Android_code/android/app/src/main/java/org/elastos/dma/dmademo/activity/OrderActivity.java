package org.elastos.dma.dmademo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.tool.Waiter;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    private final int START_SUCCESS=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, OrderActivity.class);
        context.startActivity(intent);
    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what>0){
                switch (msg.what){
                    case START_SUCCESS:{
                        //跳转到成功页
                        Intent intent = new Intent();
                        intent.setClass(OrderActivity.this, ResultActivity.class);
                        intent.putExtra("title","支付成功");
                        startActivity(intent);
                        Waiter.alertErrorMessage("支付成功，等待交易完成",getApplicationContext());

                    }
                    break;

                    default:
                        break;
                }
            }
        }
    };
    public void createOrder(View view){
        //提交订单
        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... params) {
                Map<String, Object> map=new HashMap<String, Object>();


                return 1;
            }
            protected void onPostExecute(Integer result) {
                if(result!=null&&result.intValue()==1){
                    handler.sendEmptyMessage(START_SUCCESS);
                }
            };
        }.execute();

    }
    public void goprofile(View view){
        Intent intent = new Intent();
        intent.setClass(OrderActivity.this, ProfileActivity.class);
        startActivity(intent);

    }

}
