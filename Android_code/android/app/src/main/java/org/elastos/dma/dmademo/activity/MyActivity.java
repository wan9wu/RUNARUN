package org.elastos.dma.dmademo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.adapter.MyActivityAdapter;
import org.elastos.dma.dmademo.tool.MockUtil;

public class MyActivity extends AppCompatActivity {

    private RecyclerView mList;
    private MyActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        toolbar.setTitle("我的活动");
        toolbar.setTitleTextColor(0xff000000);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        mList = findViewById(R.id.activity_list);
        mAdapter = new MyActivityAdapter();
        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.top = 40;
                outRect.left = 40;
                outRect.right = 40;
            }
        });
        mAdapter.setGames(MockUtil.mockData());
    }

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyActivity.class);
        context.startActivity(intent);
    }

}
