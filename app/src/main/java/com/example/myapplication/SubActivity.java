package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    TextView rcv_id;
    TextView rcv_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        rcv_id = findViewById(R.id.rcv_id);
        rcv_pw = findViewById(R.id.rcv_pw);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");
        rcv_id.setText(id);
        rcv_pw.setText(pw);
    }
}