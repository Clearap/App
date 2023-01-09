package com.example.myapplication;

public class ListViewAdapterData
{
    private String filename;
    private String filepath;

    public void setfilename(String filename){
        this.filename = filename;
    }
    public void setfilepath(String filepath){
        this.filepath = filepath;
    }

    public String getfilename(){
        return this.filename;
    }
    public String getfilepath(){
        return this.filepath;
    }
}
