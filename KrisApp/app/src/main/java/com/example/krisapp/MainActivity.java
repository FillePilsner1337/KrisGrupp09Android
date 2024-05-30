package com.example.krisapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

/**
 * Huvudaktiviteten för Krisappen.
 * @Author Filip Claesson
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Skapar aktiviteten och sätter upp användargränssnittet.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton buttonVma = findViewById(R.id.buttonVma);
        buttonVma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VMAACTIVITY.class);
                startActivity(intent);
            }
        });

        AppCompatButton buttonKarta = findViewById(R.id.buttonKarta);
        buttonKarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}