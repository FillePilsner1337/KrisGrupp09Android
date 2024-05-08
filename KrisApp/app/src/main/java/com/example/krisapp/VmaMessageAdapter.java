package com.example.krisapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VmaMessageAdapter extends RecyclerView.Adapter<VmaMessageAdapter.VmaMessageViewHolder> {
    private List<VMAMessageObject> messages;

    public VmaMessageAdapter(List<VMAMessageObject> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public VmaMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new VmaMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VmaMessageViewHolder holder, int position) {
        VMAMessageObject message = messages.get(position);
        holder.messageTextView.setText(message.toString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class VmaMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        public VmaMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
