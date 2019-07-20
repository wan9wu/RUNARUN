package org.elastos.dma.dmademo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.activity.DetailActivity;
import org.elastos.dma.dmademo.bean.Game;

import java.util.ArrayList;
import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.HomeFeedViewHolder> {

    private List<Game> mGames = new ArrayList<>();
    private Context mContext;
    private int[] mImages = {R.mipmap.bg_liu, R.mipmap.bg_meizhou, R.mipmap.bg_nanluo, R.mipmap.bg_shaghai, R.mipmap.bg_wuhun};

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
        final Game game = mGames.get(i);
        viewHolder.cover.setImageDrawable(mContext.getDrawable(mImages[i % mImages.length]));
//        viewHolder.cover.setBackground(R.drawable.bg_alert_b);
        viewHolder.name.setText(game.getName());
        viewHolder.content.setText(game.getProduction());
        viewHolder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.launch(mContext, game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public static class HomeFeedViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView content;
        ImageView cover;
        CardView bg;

        public HomeFeedViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            cover = itemView.findViewById(R.id.cover);
            bg = itemView.findViewById(R.id.cardview);
        }
    }
}
