package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ImageView iv_img;
    TextView tv_filename;
    Button btn_delete;
    myDBHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        iv_img = findViewById(R.id.iv_img);
        tv_filename = findViewById(R.id.tv_filename);
        btn_delete = findViewById(R.id.btn_delete);
        myDBHelper = new myDBHelper(this);

        Intent intent = getIntent();
        String userid = intent.getStringExtra("userid");
        String filename = intent.getStringExtra("filename");
        String filepath = intent.getStringExtra("filepath");
        File imgFile = new File(filepath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv_img.setImageBitmap(myBitmap);
        }

        tv_filename.setText(filename);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDBHelper.deleteFileData(userid, filename);
                File file = new File(filepath);
                file.delete();

                Toast.makeText(getApplicationContext(), "삭제 완료! 다시 로그인 해주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}