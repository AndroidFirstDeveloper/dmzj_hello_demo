package com.example.myapplication.recyclerview;

public class RvTestModel {

    private String imagePath;
    private String title;
    private String content;
    private  byte praise;//0:未点赞 1：点赞
    private byte imageType;//0:雪乡1  1：雪乡2

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte getPraise() {
        return praise;
    }

    public void setPraise(byte praise) {
        this.praise = praise;
    }

    public byte getImageType() {
        return imageType;
    }

    public void setImageType(byte imageType) {
        this.imageType = imageType;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof RvTestModel))
            return false;
        if (!(obj.hashCode() == hashCode()))
            return false;
        return true;
    }
}
