package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    public interface OnItemClickListener{
        void onItemClick(View view, int pos);
    }
    private OnItemClickListener mListener = null;
    private ArrayList<ImgFile> myFileList = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView filename;
        TextView filesize;
        ViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(view, pos);

                            notifyDataSetChanged();
                        }
                    }
                }
            });

            imageView = itemView.findViewById(R.id.imageView2);
            filename = itemView.findViewById(R.id.filename);
            filesize = itemView.findViewById(R.id.filesize);
        }
    }
    public RecyclerViewAdapter(ArrayList<ImgFile> fileList)
    {
        this.myFileList = fileList;
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
        return myFileList.size();
    }
}
