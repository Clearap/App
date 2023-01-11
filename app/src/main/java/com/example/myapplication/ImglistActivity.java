package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class ImglistActivity extends AppCompatActivity{
//    ListView db_file_list;
    Button btn_upload;
    TextView tv_userid;
    private ArrayList<ImgFile> fileList;
    RecyclerView recyclerview;
    LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imglist);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerView);

//        db_file_list = (ListView)findViewById(R.id.db_file_list);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        tv_userid = (TextView)findViewById(R.id.tv_userid);
//        adapter = new ListViewAdapter();

        String userid = getIntent().getStringExtra("userid");
        String welcome = userid + "님 반갑습니다.";
        tv_userid.setText(welcome);
//        displayList(userid);
        InitializeData(userid);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(ImglistActivity.this, UploadActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });

//        db_file_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
////                String filename = ((ListViewAdapterData)adapter.getItem(position)).getfilename();
////                String filepath = ((ListViewAdapterData)adapter.getItem(position)).getfilepath();
//                Intent intent = new Intent(ImglistActivity.this, ViewActivity.class);
//                intent.putExtra("userid", userid);
////                intent.putExtra("filename", filename);
////                intent.putExtra("filepath", filepath);
//                startActivity(intent);
//
//            }
//        });
        recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String filename = ((ListViewAdapterData)adapter.getItem(position)).getfilename();
//                String filepath = ((ListViewAdapterData)adapter.getItem(position)).getfilepath();
                Intent intent = new Intent(ImglistActivity.this, ViewActivity.class);
                intent.putExtra("userid", userid);
//                intent.putExtra("filename", filename);
//                intent.putExtra("filepath", filepath);
                startActivity(intent);

            }
        });
    }
//    void displayList(String userid){
//        myDBHelper myDBHelper = new myDBHelper(this);
//
//        SQLiteDatabase database = myDBHelper.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM FILETABLE WHERE userid = ?", new String[] {userid});

//        while(cursor.moveToNext()){
//            adapter.addItemToList(cursor.getString(1), cursor.getString(2));
//        }
//        db_file_list.setAdapter(adapter);
//    }

    public void InitializeData(String userid){
        myDBHelper myDBHelper = new myDBHelper(this);
        SQLiteDatabase database = myDBHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FILETABLE WHERE userid = ?", new String[] {userid});

        while(cursor.moveToNext()){
            fileList.add(new ImgFile(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(new RecyclerViewAdapter(fileList));
    }
}
