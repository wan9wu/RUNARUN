package org.elastos.dma.dmademo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.adapter.MyTicketAdapter;
import org.elastos.dma.dmademo.bean.Game;
import org.elastos.dma.dmademo.net.HttpEngine;
import org.elastos.dma.dmademo.tool.MockUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class MyTicketActivity extends AppCompatActivity {

    private RecyclerView mList;
    private MyTicketAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        toolbar.setTitle("已购票务");
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
        mList = findViewById(R.id.ticket_list);
        mAdapter = new MyTicketAdapter();
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
        fetchTickets();
    }

    private void fetchTickets() {
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                Map<String, String> map = new HashMap<>();
                map.put("did", "1001");
                Response response = HttpEngine.sendGetRequest("/maven_demo/qryUserTicketList.do", map);
                try {
                    if (response == null) {
                        return null;
                    } else {
                        String result = response.body().string();

                        Log.i("Result", result);
                        return result;
                    }
                } catch (IOException e) {

                }
                return null;
            }
            protected void onPostExecute(String returnJson) {
                if (TextUtils.isEmpty(returnJson)) {
                    mAdapter.setGames(MockUtil.mockData());
                } else {
                    try {
                        List<Game> result = JSON.parseArray(returnJson, Game.class);
                        mAdapter.setGames(result);
                    } catch (JSONException e) {
                        mAdapter.setGames(MockUtil.mockData());
                    }
                }
            };
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyTicketActivity.class);
        context.startActivity(intent);
    }

}
