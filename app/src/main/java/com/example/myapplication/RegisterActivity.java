package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
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
    private DbOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDbOpenHelper = new DbOpenHelper(this);
        btn_submit = findViewById(R.id.btn_submit);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_pw2 = findViewById(R.id.et_pw2);
        et_email = findViewById(R.id.et_email);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_id = et_id.getText().toString();
                str_pw = et_pw.getText().toString();
                str_pw2 = et_pw2.getText().toString();
                str_email = et_email.getText().toString();
                if(str_pw == str_pw2){
                    if(mDbOpenHelper.insertColumn(str_id, str_pw, str_email) > 0){
                        finish();
                    }else{
                        Toast registfail = Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT);
                        registfail.show();
                    }
                }else{
                    Toast registfail = Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다", Toast.LENGTH_SHORT);
                    registfail.show();
                }
            }
        });
    }
}