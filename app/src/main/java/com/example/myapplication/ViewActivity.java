package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    TextView tv_filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        tv_filename = findViewById(R.id.tv_filename);

        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");

        tv_filename.setText(filename);
    }
}