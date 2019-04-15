package com.example.myapplication.recyclerview;

import java.util.List;

public class NestModel {

    private int position;
    private List<ItemModel> images;
    private int offset;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<ItemModel> getImages() {
        return images;
    }

    public void setImages(List<ItemModel> images) {
        this.images = images;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static class ItemModel {
        private String title;
        private String path;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
