package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
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
    TextView tv_instorage, tv_fname, tv_fsize, tv_fpath;
    String fname, fsize, fpath;
    myDBHelper myDBHelper;
    Bitmap imgBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        myDBHelper = new myDBHelper(this);
        upload_img = (ImageView)findViewById(R.id.upload_img);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);
        btn_path = (Button) findViewById(R.id.btn_path);
        tv_instorage = (TextView)findViewById(R.id.tv_instorage);
        tv_fname = (TextView)findViewById(R.id.tv_fname);
        tv_fsize = (TextView)findViewById(R.id.tv_fsize);
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
                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(fpath)){
                    Toast.makeText(getApplicationContext(), "파일을 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    saveBitmapToJpeg(imgBitmap);
                    Intent intent = new Intent();
                    boolean insert = myDBHelper.insertFileData(getIntent().getStringExtra("userid"), fname, fpath);
                    if(insert == true){
                        Toast.makeText(getApplicationContext(), "업로드 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                }
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
                imgBitmap = BitmapFactory.decodeStream(ips);
                upload_img.setImageBitmap(imgBitmap);
                Cursor returnCursor = getContentResolver().query(fileUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                fname = returnCursor.getString(nameIndex);
                fpath = fileUri.getPath();
                fsize = Long.toString(returnCursor.getLong(sizeIndex));
                tv_fname.setText(fname);
                tv_fpath.setText(fpath);
                tv_fsize.setText(fsize);
                ips.close();

                Toast.makeText(getApplicationContext(), "파일 불러오기 성공!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "파일 불러오기 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveBitmapToJpeg(Bitmap bitmap){
        File tempFile = new File(getCacheDir(), fname);
        try{
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            Toast.makeText(getApplicationContext(), "파일 저장 성공!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "파일 저장 실패!", Toast.LENGTH_SHORT).show();
        }
    }
}