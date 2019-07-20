package org.elastos.dma.dmademo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.bean.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    List<Message> mMessages = new ArrayList<>();
    private Context mContext;

    public void setMessages(List<Message> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext= viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, viewGroup, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder viewHolder, int i) {
        Message message = mMessages.get(i);
        viewHolder.mTitle.setText(message.getTitle());
        viewHolder.mContent.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mContent;

        public MessageViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.message_title);
            mContent = view.findViewById(R.id.message_content);
        }
    }
}
