package com.androidads.adsdemo.model;

import java.io.Serializable;

public class QurekaandPreadChampAdvertiseModel implements Serializable {
    String header = "";
    String title = "";
    String body = "";
    String url = "";
    int banner;
    int gifView;

    public QurekaandPreadChampAdvertiseModel(String header, String title, String body, int banner, int gifView, String url) {
        this.header = header;
        this.title = title;
        this.body = body;
        this.gifView = gifView;
        this.banner = banner;
        this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public int getGifView() {
        return gifView;
    }

    public void setGifView(int gifView) {
        this.gifView = gifView;
    }
}
