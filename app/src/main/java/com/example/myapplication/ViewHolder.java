package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView filename;
    TextView filesize;

    ViewHolder(View itemView){
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView2);
        filename = itemView.findViewById(R.id.filename);
        filesize = itemView.findViewById(R.id.filesize);
    }
}
