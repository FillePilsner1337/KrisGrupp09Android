package com.example.krisapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VmaMessageAdapter extends RecyclerView.Adapter<VmaMessageAdapter.VmaMessageViewHolder> {
    List<VMAMessageObject> messages;
    private RecyclerViewClickListener listener;

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public VmaMessageAdapter(List<VMAMessageObject> messages, RecyclerViewClickListener listener) {
        this.messages = messages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VmaMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new VmaMessageViewHolder(view, listener);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView vmaMessageExtended;
        public MyViewHolder(final View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
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

        public VmaMessageViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            itemView.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
        }
    }
}
