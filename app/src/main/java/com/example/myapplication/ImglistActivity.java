package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ImglistActivity extends AppCompatActivity {

    ListView img_list;
    Button btn_upload;
    TextView tv_userid;
    myDBHelper myDBHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imglist);

        img_list = (ListView)findViewById(R.id.img_list);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        tv_userid = (TextView)findViewById(R.id.tv_userid);

        myDBHelper = new myDBHelper(this);
        Intent intent = new Intent(ImglistActivity.this, UploadActivity.class);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1); // 리스트의 연결
        ArrayList<String> Datas = new ArrayList<String>();
        String userid = getIntent().getStringExtra("userid");
        String welcome = userid + "님 반갑습니다.";
        img_list.setAdapter(adapter);
        tv_userid.setText(userid);
        db = myDBHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT filename, filepath FROM FILETABLE WHERE userid = ?", new String [] {userid});
        try {
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    Datas.add(cursor.getString(1));
                    Datas.add(cursor.getString(2));
                }
            }
        }finally {
            cursor.close();
        }

        adapter.notifyDataSetChanged(); // 저장

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
    }
}