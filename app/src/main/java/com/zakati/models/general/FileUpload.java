package com.zakati.models.general;

/**
 * Created by rahil on 29/11/16.
 */

public class FileUpload {

    public static final  int STATUS_NORMAL = 1;
    public static final  int STATUS_UPLOADING= 2;
    public static final  int STATUS_UPLOADED = 3;
    private long id;
    private int status = STATUS_NORMAL;
    private String localPath,url,imageType;


    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
