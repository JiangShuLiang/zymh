package com.zymh.www.zymh.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicGroupData implements Parcelable {
    public static final Creator<ComicGroupData> CREATOR = new Creator<ComicGroupData>() {
        @Override
        public ComicGroupData createFromParcel(Parcel source) {
            ComicGroupData var = new ComicGroupData();
            var.books = source.createTypedArray(ComicGroupDataBooks.CREATOR);
            var.more = source.readByte() != 0;
            return var;
        }

        @Override
        public ComicGroupData[] newArray(int size) {
            return new ComicGroupData[size];
        }
    };
    private ComicGroupDataBooks[] books;
    private boolean more;

    public ComicGroupDataBooks[] getBooks() {
        return this.books;
    }

    public void setBooks(ComicGroupDataBooks[] books) {
        this.books = books;
    }

    public boolean getMore() {
        return this.more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.books, flags);
        dest.writeByte(more ? (byte) 1 : (byte) 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
