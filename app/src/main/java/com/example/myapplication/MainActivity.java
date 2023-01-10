package com.example.myapplication;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_PERMISSION_CALLBACK = 100;

    EditText et_id;
    EditText et_pw;
    Button btn_login;
    Button btn_register;
    String str_id;
    String str_pw;
    myDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPermissionGrantedNRequestPermission(WRITE_EXTERNAL_STORAGE, REQ_PERMISSION_CALLBACK);
        isPermissionGrantedNRequestPermission(READ_EXTERNAL_STORAGE, REQ_PERMISSION_CALLBACK);

        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        myDBHelper = new myDBHelper(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_id = et_id.getText().toString();
                str_pw = et_pw.getText().toString();
                if(TextUtils.isEmpty(str_id) || TextUtils.isEmpty(str_pw)){
                    Toast.makeText(getApplicationContext(),"아이디와 비밀번호는 필수 입력입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkuserpass = myDBHelper.checkusernamepassword(str_id, str_pw);
                    if(checkuserpass == true){
                        Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ImglistActivity.class);
                        intent.putExtra("userid", str_id);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isPermissionGranted(String strPermission){
        boolean blsPermissionGranted = true;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, strPermission)){
                blsPermissionGranted = false;
            }else{
                blsPermissionGranted = true;
            }
        }
        return blsPermissionGranted;
    }

    public boolean isPermissionGrantedNRequestPermission(String strPermission, int iCallback){
        boolean blsPermissionGranted = isPermissionGranted(strPermission);

        if(!blsPermissionGranted){
            boolean blsDeny = ActivityCompat.shouldShowRequestPermissionRationale(this, strPermission);
            if(blsDeny){
                this.requestPermissions(new String[]{strPermission}, iCallback);
                blsPermissionGranted = false;
            }else{
                ActivityCompat.requestPermissions(this, new String[]{strPermission}, iCallback);
                blsPermissionGranted = true;
            }
        }
        return blsPermissionGranted;
    }
}