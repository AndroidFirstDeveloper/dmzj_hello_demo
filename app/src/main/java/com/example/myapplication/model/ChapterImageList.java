package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class ChapterImageList implements Serializable {


    /**
     * id : 13
     * comic_id : 5
     * num : 1
     * type : 1
     * direction : 2
     * form : 2
     * cover : http://img.myfcomic.com/images/upin_comics/5/chapter/13/f5173624839a05af4f943daa1ec367d4.jpg
     * images : dsd
     * order : 1
     * status : 1
     * editor : null
     * created_at : 2018-07-11 19:09:26
     * updated_at : 2018-07-12 10:16:08
     * chapter_qian : 0
     * chapter_hou : 14
     * chapter_title : 第一话
     * viewpoint_count : 0
     * viewpoint_list : []
     * "cover_vertical":"/images/upin_comics/6/chapter/72/0349384ab49d1e48f8285bed4eecda2a.jpg",
     * "is_charge":2,
     * "comic_title":"冰菓",
     * "share_url":"http://m.myfcomic.com/comic/148",
     * "share_wechat_url":"/pages/caricatureDetail/main?id=148"
     */


    private int id;//章节id
    private int comic_id;//漫画id
    private int num;
    private int type;
    private int direction;
    private int form;
    private String cover;
    private List<ImageBean> images;
    private int order;
    private int status;
    private int editor;
    private String created_at;
    private String updated_at;
    private int chapter_qian;
    private int chapter_hou;
    private String chapter_title;
    private int user_charge;
    private int chapter_charge;

    private int viewpoint_count;
    private List<?> viewpoint_list;

    private String cover_vertical;
    private int is_charge;
    private String comic_title;
    private String share_url;
    private String share_wechat_url;

    private String chapter_num;//: "番外8",
    private int chapter_num_special;//: 1,

    public String getChapter_num() {
        return chapter_num;
    }

    public void setChapter_num(String chapter_num) {
        this.chapter_num = chapter_num;
    }

    public int getChapter_num_special() {
        return chapter_num_special;
    }

    public void setChapter_num_special(int chapter_num_special) {
        this.chapter_num_special = chapter_num_special;
    }

    public String getCover_vertical() {
        return cover_vertical;
    }

    public void setCover_vertical(String cover_vertical) {
        this.cover_vertical = cover_vertical;
    }

    public int getIs_charge() {
        return is_charge;
    }

    public void setIs_charge(int is_charge) {
        this.is_charge = is_charge;
    }

    public String getComic_title() {
        return comic_title;
    }

    public void setComic_title(String comic_title) {
        this.comic_title = comic_title;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_wechat_url() {
        return share_wechat_url;
    }

    public void setShare_wechat_url(String share_wechat_url) {
        this.share_wechat_url = share_wechat_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<ImageBean> getImages() {
        return images;
    }

    public void setImages(List<ImageBean> images) {
        this.images = images;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEditor() {
        return editor;
    }

    public void setEditor(int editor) {
        this.editor = editor;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getChapter_qian() {
        return chapter_qian;
    }

    public void setChapter_qian(int chapter_qian) {
        this.chapter_qian = chapter_qian;
    }

    public int getChapter_hou() {
        return chapter_hou;
    }

    public void setChapter_hou(int chapter_hou) {
        this.chapter_hou = chapter_hou;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public int getViewpoint_count() {
        return viewpoint_count;
    }

    public void setViewpoint_count(int viewpoint_count) {
        this.viewpoint_count = viewpoint_count;
    }

    public int getUser_charge() {
        return user_charge;
    }

    public void setUser_charge(int user_charge) {
        this.user_charge = user_charge;
    }

    public int getChapter_charge() {
        return chapter_charge;
    }

    public void setChapter_charge(int chapter_charge) {
        this.chapter_charge = chapter_charge;
    }

    public List<?> getViewpoint_list() {
        return viewpoint_list;
    }

    public void setViewpoint_list(List<?> viewpoint_list) {
        this.viewpoint_list = viewpoint_list;
    }
}
