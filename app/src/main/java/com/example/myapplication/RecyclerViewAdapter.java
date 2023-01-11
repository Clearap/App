package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{
    private ArrayList<ImgFile> myFileList = null;

    RecyclerViewAdapter(ArrayList<ImgFile> fileList){
        myFileList = fileList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.file_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        String strImg = myFileList.get(position).getFilePath();
        File file = new File(strImg);
        Bitmap myBitmap = BitmapFactory.decodeFile(strImg);
        viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewHolder.imageView.setImageBitmap(myBitmap);
        viewHolder.filename.setText(myFileList.get(position).getFileName());
        viewHolder.filesize.setText(myFileList.get(position).getFileSize());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
