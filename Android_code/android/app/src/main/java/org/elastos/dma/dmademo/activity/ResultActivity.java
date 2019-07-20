package org.elastos.dma.dmademo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        if(intent.getStringExtra("title")!=null){
            TextView tv_title=findViewById(R.id.tv_title);
            tv_title.setText(intent.getStringExtra("title"));
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ResultActivity.class);
        context.startActivity(intent);
    }
    public void goDetail(View view){
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);
        startActivity(intent);
    }

}
