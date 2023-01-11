package com.example.myapplication;

public class ImgFile {
    private String fileName;
    private String filePath;
    private String fileSize;

    public ImgFile(String fileName, String filePath, String fileSize){
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public String getFileName(){
        return fileName;
    }

    public String getFilePath(){
        return filePath;
    }

    public String getFileSize(){
        return fileSize;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setFileSize(String fileSize){
        this.fileSize = fileSize;
    }
}
