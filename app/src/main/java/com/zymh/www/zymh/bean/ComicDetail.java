package com.zymh.www.zymh.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicDetail implements Parcelable {
    public static final Creator<ComicDetail> CREATOR = new Creator<ComicDetail>() {
        @Override
        public ComicDetail createFromParcel(Parcel source) {
            ComicDetail var = new ComicDetail();
            var.msg = source.readString();
            var.code = source.readInt();
            var.data = source.readParcelable(ComicDetailData.class.getClassLoader());
            return var;
        }

        @Override
        public ComicDetail[] newArray(int size) {
            return new ComicDetail[size];
        }
    };
    private String msg;
    private int code;
    private ComicDetailData data;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ComicDetailData getData() {
        return this.data;
    }

    public void setData(ComicDetailData data) {
        this.data = data;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeInt(this.code);
        dest.writeParcelable(this.data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
