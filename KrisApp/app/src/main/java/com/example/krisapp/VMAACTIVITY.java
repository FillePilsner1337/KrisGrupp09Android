package com.example.krisapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class VMAACTIVITY extends AppCompatActivity implements VmaController.MessageDisplayer{
    private VmaController vmaController;
    private TextView messageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vmaactivity);

        messageTextView = findViewById(R.id.messageTextView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vmaController = new VmaController(this);
        vmaController.fetchAndDisplayMessage();
    }


    @Override
    public void displayMessages(List<VMAMessageObject> messages) {
        StringBuilder builder = new StringBuilder();
        for (VMAMessageObject message : messages) {
            builder.append(message.toString()).append("\n\n");
        }
        messageTextView.setText(builder.toString());
    }
}