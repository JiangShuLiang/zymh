package com.zymh.www.zymh.bean;

/**
 * Created by Dell on 2018/7/31.
 */

public class TestBean {
    String title;
    String subtitle;
    String picUrl;

    public TestBean(String title, String subtitle, String picUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
