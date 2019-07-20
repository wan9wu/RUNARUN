package org.elastos.dma.dmademo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.activity.DetailActivity;
import org.elastos.dma.dmademo.bean.Game;
import org.elastos.dma.dmademo.view.AlertDialogUtil;

import java.util.ArrayList;
import java.util.List;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.MyTicketViewHolder> {

    private List<Game> games = new ArrayList<>();
    private Context mContext;

    public void setGames(List<Game> gameList) {
        games.clear();
        games.addAll(gameList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyTicketAdapter.MyTicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ticket, viewGroup, false);
        return new MyTicketAdapter.MyTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketAdapter.MyTicketViewHolder viewHolder, int i) {
        final Game game = games.get(i);
        viewHolder.location.setText("北京");
        viewHolder.time.setText("6月15日");
        viewHolder.title.setText(game.getName());
        viewHolder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.launch(mContext, game);
            }
        });
        viewHolder.give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogUtil.showUtil(mContext, "转出价", "确认转卖", true);
            }
        });
        viewHolder.sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogUtil.showUtil(mContext, "转赠人DID", "确认转赠", false);
//                Toast.makeText(mContext, "赠送成功", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    public static class MyTicketViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView location;
        TextView time;
        LinearLayout bg;
        TextView give;
        TextView sale;

        public MyTicketViewHolder(View item) {
            super(item);
            time = item.findViewById(R.id.item_ticket_time);
            title = item.findViewById(R.id.item_ticket_title);
            location = item.findViewById(R.id.item_ticket_location);
            bg = item.findViewById(R.id.my_ticket_bg);
            give = item.findViewById(R.id.give);
            sale = item.findViewById(R.id.sale);
        }
    }
}
