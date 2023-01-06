package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
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
}