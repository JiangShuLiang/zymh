package com.zymh.www.zymh.bean;

public class HomePageData {
    private HomePageDataHeader[] header;
    private HomePageDataBody[] body;

    public HomePageDataHeader[] getHeader() {
        return this.header;
    }

    public void setHeader(HomePageDataHeader[] header) {
        this.header = header;
    }

    public HomePageDataBody[] getBody() {
        return this.body;
    }

    public void setBody(HomePageDataBody[] body) {
        this.body = body;
    }
}
