package org.elastos.dma.dmademo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.bean.Game;

import java.util.ArrayList;
import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.HomeFeedViewHolder> {

    private List<Game> mGames = new ArrayList<>();
    private Context mContext;

    public HomeFeedAdapter(Context context) {
        mContext = context;
    }

    public void setGames(List<Game> gameList) {
        mGames.addAll(gameList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game, viewGroup, false);
        return new HomeFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFeedAdapter.HomeFeedViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public static class HomeFeedViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView content;
        ImageView cover;


        public HomeFeedViewHolder(View itemView) {
            super(itemView);

        }
    }
}
