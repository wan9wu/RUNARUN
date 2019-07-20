package org.elastos.dma.dmademo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.elastos.dma.dmademo.bean.Game;

import java.util.ArrayList;
import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter {

    private List<Game> mGames = new ArrayList<>();

    public HomeFeedAdapter() {

    }

    public void setGames(List<Game> gameList) {
        mGames.addAll(gameList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public static class HomeFeedViewHolder extends RecyclerView.ViewHolder {

        public HomeFeedViewHolder(View itemView) {
            super(itemView);
        }
    }
}
