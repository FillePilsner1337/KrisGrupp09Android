package com.example.krisapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VmaNewWindowFromObjectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vmanewwindowfromobject);

        TextView headlineView = findViewById(R.id.headlineText);
        TextView bodyTextView = findViewById(R.id.bodyText);
        TextView publishedView = findViewById(R.id.publishedText);

        TextView detailedInfoView = findViewById(R.id.detailedInfoText);

        String detailedInfo = getIntent().getStringExtra("detailedInfo");
        String headline = getIntent().getStringExtra("headline");
        String bodyText = getIntent().getStringExtra("bodyText");
        String publishedText = getIntent().getStringExtra("published");

        headlineView.setText(headline);
        bodyTextView.setText(bodyText);
        publishedView.setText(publishedText);
        detailedInfoView.setText(detailedInfo);
    }
}
