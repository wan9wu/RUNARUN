package org.elastos.dma.dmademo;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.elastos.dma.dmademo.adapter.HomeFeedAdapter;
import org.elastos.dma.dmademo.bean.Game;
import org.elastos.dma.dmademo.net.MyTask;

import java.util.ArrayList;
import java.util.List;

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
        mAdapter.setGames(mockData());
    }

    private List<Game> mockData() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Game game = new Game();
            game.setName("马拉松");
            game.setProduction("关于2018上海国际马拉松赛违规参赛者的处罚公告. 2018/12/03. 2018上海国际马拉松赛前20名成绩公示");
            games.add(game);
        }
        return games;
    }

    private void getGames() {
        AsyncTask task = new MyTask() {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }

            @Override
            protected String doInBackground(String... params) {
                return super.doInBackground(params);
            }
        };
        task.execute();
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
