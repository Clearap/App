package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class UploadActivity extends AppCompatActivity {

    ImageView upload_img;
    Button btn_upload;
    Button btn_path;
    DrawerLayout drawerLayout;
    View drawerView;
    TextView tv_instorage, tv_fname, tv_fpath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        upload_img = (ImageView)findViewById(R.id.upload_img);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);
        btn_path = (Button) findViewById(R.id.btn_path);
        tv_instorage = (TextView)findViewById(R.id.tv_instorage);
        tv_fname = (TextView)findViewById(R.id.tv_fname);
        tv_fpath = (TextView)findViewById(R.id.tv_fpath);

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "업로드 할 이미지", Toast.LENGTH_SHORT).show();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });
        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });
        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        tv_instorage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 || resultCode != RESULT_OK) {
            Uri fileUri = data.getData();
            ContentResolver resolver = getContentResolver();
            try {
                InputStream ips = resolver.openInputStream(fileUri);
                Bitmap imgBitmap = BitmapFactory.decodeStream(ips);
                upload_img.setImageBitmap(imgBitmap);
                Cursor returnCursor = getContentResolver().query(fileUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                tv_fname.setText(returnCursor.getShort(nameIndex));
                tv_fpath.setText(Long.toString(returnCursor.getLong(sizeIndex)));
                ips.close();
//                saveBitmapToJpeg(imgBitmap);
                Toast.makeText(getApplicationContext(), "파일 불러오기 성공!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "파일 불러오기 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public void saveBitmapToJpeg(Bitmap bitmap){
//        File tempFile = new File(getCacheDir(), imgName);
//        try{
//            tempFile.createNewFile();
//            FileOutputStream out = new FileOutputStream(tempFile);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.close();
//            Toast.makeText(getApplicationContext(), "파일 저장 성공!", Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), "파일 저장 실패!", Toast.LENGTH_SHORT).show();
//        }
//    }
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}