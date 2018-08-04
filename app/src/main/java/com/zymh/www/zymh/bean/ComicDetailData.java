package com.zymh.www.zymh.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicDetailData implements Parcelable {
    public static final Creator<ComicDetailData> CREATOR = new Creator<ComicDetailData>() {
        @Override
        public ComicDetailData createFromParcel(Parcel source) {
            ComicDetailData var = new ComicDetailData();
            var.chapters = source.createTypedArray(ComicDetailDataChapters.CREATOR);
            var.book = source.readParcelable(ComicDetailDataBook.class.getClassLoader());
            return var;
        }

        @Override
        public ComicDetailData[] newArray(int size) {
            return new ComicDetailData[size];
        }
    };
    private ComicDetailDataChapters[] chapters;
    private ComicDetailDataBook book;

    public ComicDetailDataChapters[] getChapters() {
        return this.chapters;
    }

    public void setChapters(ComicDetailDataChapters[] chapters) {
        this.chapters = chapters;
    }

    public ComicDetailDataBook getBook() {
        return this.book;
    }

    public void setBook(ComicDetailDataBook book) {
        this.book = book;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.chapters, flags);
        dest.writeParcelable(this.book, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
