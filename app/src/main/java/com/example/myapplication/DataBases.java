package com.example.myapplication;

import android.provider.BaseColumns;

public class DataBases {
    public static final class CreateDB implements BaseColumns{
        public static final String USERID = "userid";
        public static final String USERPW = "userpw";
        public static final String USEREMAIL = "useremail";
        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer auto increment, "
                + USERID + " text not null primary key, "
                + USERPW + " text not null, "
                + USEREMAIL + "text not null);";
    }
}
