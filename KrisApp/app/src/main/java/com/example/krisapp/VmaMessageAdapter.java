package com.example.krisapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter för att hantera och visa VMA-meddelanden i en RecyclerView.
 *
 * Denna klass används i VMAACTIVITY för att visa en lista med VMA-meddelanden.
 * Adaptern hanterar visningen och interaktionen med varje VMA-meddelande i listan.
 *
 * @Author Filip Claesson
 */
public class VmaMessageAdapter extends RecyclerView.Adapter<VmaMessageAdapter.VmaMessageViewHolder> {
    List<VMAMessageObject> messages;
    private RecyclerViewClickListener listener;

    /**
     * Interface för att hantera klickhändelser på RecyclerView-element, dvs varje VMA-meddelande.
     * @Author Filip Claesson
     */
    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    /**
     * Constructor för VmaMessageAdapter.
     * @Author Filip Claesson
     */
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

    /**
     * Visar data på den angivna positionen i RecyclerView.
     * @Author Filip Claesson
     */
    @Override
    public void onBindViewHolder(@NonNull VmaMessageViewHolder holder, int position) {
        VMAMessageObject message = messages.get(position);
        holder.messageTextView.setText(message.toString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * ViewHolder-klass för att hantera varje VMA-meddelande i RecyclerView.
     * @Author Filip Claesson
     */
    public static class VmaMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        public VmaMessageViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            itemView.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
        }
    }
}
