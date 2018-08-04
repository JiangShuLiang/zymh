package com.zymh.www.zymh.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UpdatePage implements Parcelable {
    public static final Creator<UpdatePage> CREATOR = new Creator<UpdatePage>() {
        @Override
        public UpdatePage createFromParcel(Parcel source) {
            UpdatePage var = new UpdatePage();
            var.msg = source.readString();
            var.code = source.readInt();
            var.data = source.createTypedArray(UpdatePageData.CREATOR);
            return var;
        }

        @Override
        public UpdatePage[] newArray(int size) {
            return new UpdatePage[size];
        }
    };
    private String msg;
    private int code;
    private UpdatePageData[] data;

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

    public UpdatePageData[] getData() {
        return this.data;
    }

    public void setData(UpdatePageData[] data) {
        this.data = data;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeInt(this.code);
        dest.writeTypedArray(this.data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
