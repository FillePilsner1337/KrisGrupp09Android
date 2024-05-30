package com.example.krisapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Aktiviteten för att visa och hantera Viktiga Meddelande till Allmänheten(VMA).
 * @Author Filip Claesson
 */
public class VMAACTIVITY extends AppCompatActivity implements MessageDisplayer{
    private VmaController vmaController;
    private RecyclerView messagesRecyclerView;
    private VmaMessageAdapter adapter;
    private TextView messageTextView;
    private VmaMessageAdapter.RecyclerViewClickListener listener;

    /**
     * Metoden som körs när aktiviteten skapas.
     * Ställer in layouten för aktiviteten, initierar RecyclerView och laddar VMA-meddelanden.
     * @Author Filip Claesson
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vmaactivity);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupClickListener();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vmaController = new VmaController(this);
        vmaController.fetchAndDisplayMessage();

        adapter = new VmaMessageAdapter(new ArrayList<>(), listener);
        messagesRecyclerView.setAdapter(adapter);
    }

    /**
     * Initierar Clicklistener för att hantera användarens interaktion med VMA-meddelanden.
     * @Author Filip Claesson
     */
    private void setupClickListener() {
        listener = (view, position) -> {
            VMAMessageObject selectedMessage = adapter.messages.get(position);
            Intent intent = new Intent(VMAACTIVITY.this, VmaNewWindowFromObjectActivity.class);
            intent.putExtra("detailedInfo", selectedMessage.getDetailedInfo());
            startActivity(intent);
        };
    }
    /**
     * Visar en lista med VMA-meddelanden.
     * @Author Filip Claesson
     */
    @Override
    public void displayMessages(List<VMAMessageObject> messages) {
        adapter = new VmaMessageAdapter(messages, listener);
        messagesRecyclerView.setAdapter(adapter);
    }
}