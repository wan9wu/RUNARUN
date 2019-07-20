package org.elastos.dma.dmademo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.activity.DetailActivity;
import org.elastos.dma.dmademo.bean.Game;

import java.util.ArrayList;
import java.util.List;

public class MyActivityAdapter extends RecyclerView.Adapter<MyActivityAdapter.MyActivityViewHolder> {

    private List<Game> games = new ArrayList<>();
    private Context mContext;

    public void setGames(List<Game> gameList) {
        games.clear();
        games.addAll(gameList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_activity, viewGroup, false);
        return new MyActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyActivityViewHolder viewHolder, int i) {
        final Game game = games.get(i);
        viewHolder.location.setText("上海");
        viewHolder.time.setText("6月15日");
        viewHolder.title.setText(game.getName());
        viewHolder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.launch(mContext, game);
            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    public static class MyActivityViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView location;
        TextView time;
        RelativeLayout bg;

        public MyActivityViewHolder(View item) {
            super(item);
            time = item.findViewById(R.id.item_activity_time);
            title = item.findViewById(R.id.item_activity_title);
            location = item.findViewById(R.id.item_activity_location);
            bg = item.findViewById(R.id.my_activity_bg);

        }
    }
 }
