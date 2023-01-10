package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<ListViewAdapterData> list = new ArrayList<>();

    public int getCount(){
        return list.size();
    }

    public Object getItem(int i){
        return list.get(i);
    }

    public long getItemId(int i){
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup){
        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.file_listview, viewGroup, false);
        }

        TextView tv_filepath = (TextView)view.findViewById(R.id.tv_filename);
        TextView tv_filename = (TextView)view.findViewById(R.id.tv_filepath);
        ListViewAdapterData listdata = list.get(i);

        tv_filename.setText(listdata.getfilename());
        tv_filepath.setText(listdata.getfilepath());

        return view;
    }

    public void addItemToList(String filename, String filepath){
        ListViewAdapterData listdata = new ListViewAdapterData();

        listdata.setfilename(filename);
        listdata.setfilepath(filepath);

        list.add(listdata);
    }
}
