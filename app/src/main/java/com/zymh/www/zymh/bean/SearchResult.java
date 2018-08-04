package com.zymh.www.zymh.bean;

public class SearchResult {
    private Object msg;
    private int code;
    private SearchResultData[] data;

    public Object getMsg() {
        return this.msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SearchResultData[] getData() {
        return this.data;
    }

    public void setData(SearchResultData[] data) {
        this.data = data;
    }
}
