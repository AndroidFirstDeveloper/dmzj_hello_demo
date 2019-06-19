package com.example.myapplication.model;

import java.io.Serializable;

public class ImageBean implements Serializable {


    /**
     * name : http://img.myfcomic.com/images/upin_comics/5/chapter/13/6edc72bfdffa29a0d78ccfd1c9ee906c.jpg
     * blob : http://img.myfcomic.comhttp://adminapi.myfcomic.com/images/upin_comics/5/chapter/13/6edc72bfdffa29a0d78ccfd1c9ee906c.jpg
     * width : 510
     * height : 362
     */

    private String name;
    private String blob;
    private int width;
    private int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
