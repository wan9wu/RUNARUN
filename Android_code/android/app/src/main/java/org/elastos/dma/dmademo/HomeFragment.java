package org.elastos.dma.dmademo;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import org.elastos.dma.dmademo.adapter.HomeFeedAdapter;
import org.elastos.dma.dmademo.bean.Game;
import org.elastos.dma.dmademo.net.HttpEngine;
import org.elastos.dma.dmademo.tool.MockUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mList;
    private HomeFeedAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mList = view.findViewById(R.id.home_feed);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mList.setLayoutManager(layoutManager);
        mList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.bottom = 40;
                if (itemPosition % 2 == 0) {
                    outRect.left = 40;
                } else {
                    outRect.left = 40;
                    outRect.right = 40;
                }
            }
        });
        mAdapter = new HomeFeedAdapter(getContext());
        mList.setAdapter(mAdapter);
        getGames();
    }

    private void getGames() {
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                Response response = HttpEngine.sendGetRequest("/maven_demo/qryTicketList.do", null);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
