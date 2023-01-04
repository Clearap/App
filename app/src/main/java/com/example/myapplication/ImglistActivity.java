package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ImglistActivity extends AppCompatActivity {

    ListView img_list;
    Button btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imglist);

        img_list = (ListView)findViewById(R.id.img_list);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        List<String> data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1); // 리스트의 연결
        img_list.setAdapter(adapter);

        data.add("1번이미지");
        data.add("2번이미지");
        data.add("3번이미지");
        data.add("4번이미지");
        adapter.notifyDataSetChanged(); // 저장

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImglistActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
    }

}