package org.elastos.dma.dmademo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.elastos.dma.dmademo.adapter.HomeFeedAdapter;
import org.elastos.dma.dmademo.net.MyTask;

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
        mAdapter = new HomeFeedAdapter();
        mList.setAdapter(mAdapter);
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
//        Response response = HttpEngine.sendGetRequest()
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
