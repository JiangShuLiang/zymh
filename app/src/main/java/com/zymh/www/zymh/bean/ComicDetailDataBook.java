package com.zymh.www.zymh.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicDetailDataBook implements Parcelable {
    public static final Creator<ComicDetailDataBook> CREATOR = new Creator<ComicDetailDataBook>() {
        @Override
        public ComicDetailDataBook createFromParcel(Parcel source) {
            ComicDetailDataBook var = new ComicDetailDataBook();
            var.summary = source.readString();
            var.keyWords = source.readString();
            var.updateDate = source.readInt();
            var.rating = source.readDouble();
            var.maxSaleChapter = source.readInt();
            var.bookName = source.readString();
            var.lastChapter = source.readInt();
            var.hits = source.readInt();
            var.chapterUpdate = source.readString();
            var.bookIcon = source.readString();
            var.authorName = source.readString();
            var.portraitIcon = source.readString();
            var.id = source.readInt();
            return var;
        }

        @Override
        public ComicDetailDataBook[] newArray(int size) {
            return new ComicDetailDataBook[size];
        }
    };
    private String summary;
    private String keyWords;
    private int updateDate;
    private double rating;
    private int maxSaleChapter;
    private String bookName;
    private int lastChapter;
    private int hits;
    private String chapterUpdate;
    private String bookIcon;
    private String authorName;
    private String portraitIcon;
    private int id;

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public int getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(int updateDate) {
        this.updateDate = updateDate;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMaxSaleChapter() {
        return this.maxSaleChapter;
    }

    public void setMaxSaleChapter(int maxSaleChapter) {
        this.maxSaleChapter = maxSaleChapter;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getLastChapter() {
        return this.lastChapter;
    }

    public void setLastChapter(int lastChapter) {
        this.lastChapter = lastChapter;
    }

    public int getHits() {
        return this.hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getChapterUpdate() {
        return this.chapterUpdate;
    }

    public void setChapterUpdate(String chapterUpdate) {
        this.chapterUpdate = chapterUpdate;
    }

    public String getBookIcon() {
        return this.bookIcon;
    }

    public void setBookIcon(String bookIcon) {
        this.bookIcon = bookIcon;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPortraitIcon() {
        return this.portraitIcon;
    }

    public void setPortraitIcon(String portraitIcon) {
        this.portraitIcon = portraitIcon;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.summary);
        dest.writeString(this.keyWords);
        dest.writeInt(this.updateDate);
        dest.writeDouble(this.rating);
        dest.writeInt(this.maxSaleChapter);
        dest.writeString(this.bookName);
        dest.writeInt(this.lastChapter);
        dest.writeInt(this.hits);
        dest.writeString(this.chapterUpdate);
        dest.writeString(this.bookIcon);
        dest.writeString(this.authorName);
        dest.writeString(this.portraitIcon);
        dest.writeInt(this.id);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
