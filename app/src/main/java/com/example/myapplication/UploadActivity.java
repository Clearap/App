package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ListActivity;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class UploadActivity extends AppCompatActivity {

    ImageView upload_img;
    Button btn_upload, btn_path;
    DrawerLayout drawerLayout;
    View drawerView;
    TextView tv_instorage, tv_fname, tv_fsize, tv_fpath;
    String fname, fsize, realfilepath;
    myDBHelper myDBHelper;
    Bitmap imgBitmap;
    String userid, save_path;

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
        userid = getIntent().getStringExtra("userid");

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "업로드 할 이미지", Toast.LENGTH_SHORT).show();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(realfilepath)){
                    Toast.makeText(getApplicationContext(), "파일을 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    save_path = saveBitmapToJpeg(imgBitmap, userid);
                    boolean insert = myDBHelper.insertFileData(userid, fname, save_path);
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
                realfilepath = getRealPathFromURI(fileUri, this);
                fsize = Long.toString(returnCursor.getLong(sizeIndex));
                tv_fname.setText(fname);
                tv_fpath.setText(realfilepath);
                tv_fsize.setText(fsize);
                ips.close();

                Toast.makeText(getApplicationContext(), "파일 불러오기 성공!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "파일 불러오기 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String saveBitmapToJpeg(Bitmap bitmap, String userid){
        File path = this.getDir(userid, 0);
        File tempFile = new File(path, fname);
        try{
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            Toast.makeText(getApplicationContext(), "파일 저장 성공!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "파일 저장 실패!", Toast.LENGTH_SHORT).show();
        }
        return tempFile.getPath();
    }

    private static String getRealPathFromURI(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getFilesDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }
}