package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImglistActivity extends AppCompatActivity {
    ListView db_file_list;
    Button btn_upload;
    TextView tv_userid;
    myDBHelper myDBHelper;
    ArrayList<ListViewAdapterData> filedatalist;
    ListViewAdapter adapter = new ListViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imglist);


        db_file_list = (ListView)findViewById(R.id.db_file_list);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        tv_userid = (TextView)findViewById(R.id.tv_userid);

        myDBHelper = new myDBHelper(this);
        String userid = getIntent().getStringExtra("userid");
        String welcome = userid + "님 반갑습니다.";
        tv_userid.setText(welcome);
        displayList();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(ImglistActivity.this, UploadActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });

        db_file_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String filename = adapter.getItem(position).getfilename();
//                Intent intent = new Intent(ImglistActivity.this, ViewActivity.class);
//                intent.putExtra("filename", filename);
//                startActivity(intent);
            }
        });
    }
    void displayList(){
        myDBHelper myDBHelper = new myDBHelper(this);

        SQLiteDatabase database = myDBHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FILETABLE", null);

        while(cursor.moveToNext()){
            adapter.addItemToList(cursor.getString(1), cursor.getString(2));
        }
        db_file_list.setAdapter(adapter);
    }
}