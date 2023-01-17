package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    myDBHelper myDBHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        myDBHelper = new myDBHelper(context);
        String userid, userpw;
        userid = intent.getStringExtra("userid");
        userpw = intent.getStringExtra("userpw");
        if(TextUtils.isEmpty(userid) || TextUtils.isEmpty(userpw)){
            Toast.makeText(context.getApplicationContext(), "아이디나 패스워드가 비었습니다.", Toast.LENGTH_SHORT);
        }else{
            boolean result = myDBHelper.changePassword(userid, userpw);
            if(result){
                Toast.makeText(context.getApplicationContext(), "비밀번호 변경을 완료했습니다.", Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(context.getApplicationContext(), "비밀번호 변경을 실패했습니다.", Toast.LENGTH_SHORT);
            }
        }
    }

}