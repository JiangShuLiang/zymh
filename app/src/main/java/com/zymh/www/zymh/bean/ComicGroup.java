package com.zymh.www.zymh.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicGroup implements Parcelable {
    public static final Creator<ComicGroup> CREATOR = new Creator<ComicGroup>() {
        @Override
        public ComicGroup createFromParcel(Parcel source) {
            ComicGroup var = new ComicGroup();
            var.msg = source.readString();
            var.code = source.readInt();
            var.data = source.readParcelable(ComicGroupData.class.getClassLoader());
            return var;
        }

        @Override
        public ComicGroup[] newArray(int size) {
            return new ComicGroup[size];
        }
    };
    private String msg;
    private int code;
    private ComicGroupData data;

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

    public ComicGroupData getData() {
        return this.data;
    }

    public void setData(ComicGroupData data) {
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
