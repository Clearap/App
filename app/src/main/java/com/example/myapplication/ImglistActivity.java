package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ImglistActivity extends AppCompatActivity{
    Button btn_upload, btn_logout;
    TextView tv_userid;
    ArrayList<ImgFile> fileList;
    RecyclerView recyclerview;
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager manager;
    String userid;
    myDBHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imglist);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        tv_userid = (TextView)findViewById(R.id.tv_userid);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        userid = getIntent().getStringExtra("userid");
        String welcome = userid + "님 반갑습니다.";
        InitializeData(userid);
        tv_userid.setText(welcome);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewAdapter = new RecyclerViewAdapter(fileList);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(recyclerViewAdapter);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(ImglistActivity.this, UploadActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                String filename = fileList.get(pos).getFileName();
                String filepath = fileList.get(pos).getFilePath();
                Intent intent = new Intent(ImglistActivity.this, ViewActivity.class);
                intent.putExtra("userid", userid);
                intent.putExtra("filename", filename);
                intent.putExtra("filepath", filepath);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        fileList.clear();
//        InitializeData(userid);
//        recyclerview.setLayoutManager(manager);
//        recyclerview.setAdapter(recyclerViewAdapter);
//        recyclerViewAdapter.notifyDataSetChanged();

//        recyclerViewAdapter.upDateItemList(fileList);
//    }

    public void InitializeData(String userid){
        myDBHelper = new myDBHelper(this);
        SQLiteDatabase database = myDBHelper.getReadableDatabase();
        fileList = new ArrayList<>();
            Cursor cursor = database.rawQuery("SELECT * FROM FILETABLE WHERE userid = ?", new String[] {userid});
            while(cursor.moveToNext()) {
                fileList.add(new ImgFile(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
    }
}
