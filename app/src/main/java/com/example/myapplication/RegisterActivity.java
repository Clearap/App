package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button btn_submit;
    EditText et_id;
    EditText et_pw;
    EditText et_pw2;
    EditText et_email;
    String str_id;
    String str_pw;
    String str_pw2;
    String str_email;
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_submit = findViewById(R.id.btn_submit);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_pw2 = findViewById(R.id.et_pw2);
        et_email = findViewById(R.id.et_email);
        myDBHelper = new myDBHelper(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_id = et_id.getText().toString();
                str_pw = et_pw.getText().toString();
                str_pw2 = et_pw2.getText().toString();
                str_email = et_email.getText().toString();

                if(TextUtils.isEmpty(str_id) || TextUtils.isEmpty(str_pw) || TextUtils.isEmpty(str_pw2)){
                    Toast.makeText(getApplicationContext(), "아이디와 패스워드는 필수 입력입니다.", Toast.LENGTH_SHORT);
                }else{
                    if(str_pw.equals(str_pw2)){
                        Boolean checkuser = myDBHelper.checkusername(str_id);
                        if(checkuser == false){
                            Boolean insert = myDBHelper.insertData(str_id, str_pw, str_email);
                            if(insert == true){
                                Toast.makeText(getApplicationContext(),"계정 생성 완료", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"계정 생성 실패", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}