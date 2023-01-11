package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper {
    public myDBHelper(Context context) {
        super(context, "filesDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERTABLE ( userid TEXT PRIMARY KEY, userpwd TEXT, useremail TEXT)");
        db.execSQL("CREATE TABLE FILETABLE ( userid TEXT, filename TEXT PRIMARY KEY, filepath TEXT, filesize TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERTABLE");
        db.execSQL("DROP TABLE IF EXISTS FILETABLE");
        onCreate(db);
    }

    public Boolean insertData(String userid, String userpwd, String useremail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userid", userid);
        values.put("userpwd", userpwd);
        values.put("useremail", useremail);

        long result = db.insert("USERTABLE", null, values);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean insertFileData(String userid, String filename, String filepath, String filesize){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userid", userid);
        values.put("filename", filename);
        values.put("filepath", filepath);
        values.put("filesize", filesize);

        long result = db.insert("FILETABLE", null, values);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public int deleteFileData(String userid, String filename){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("FILETABLE", "userid = ? and filename = ?", new String[] {userid, filename});
    }

    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from USERTABLE where userid=?", new String[] {username});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from USERTABLE where userid=? and userpwd=?", new String[] {username, password});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }
}
