package com.zymh.www.zymh.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicDetailDataChapters implements Parcelable {
    public static final Creator<ComicDetailDataChapters> CREATOR = new Creator<ComicDetailDataChapters>() {
        @Override
        public ComicDetailDataChapters createFromParcel(Parcel source) {
            ComicDetailDataChapters var = new ComicDetailDataChapters();
            var.summary = source.readString();
            var.updateDate = source.readInt();
            var.costCoin = source.readInt();
            var.lastUpdate = source.readInt();
            var.chapterName = source.readString();
            var.chapterIcon = source.readString();
            var.id = source.readInt();
            var.chapterIndex = source.readInt();
            return var;
        }

        @Override
        public ComicDetailDataChapters[] newArray(int size) {
            return new ComicDetailDataChapters[size];
        }
    };
    private String summary;
    private int updateDate;
    private int costCoin;
    private int lastUpdate;
    private String chapterName;
    private String chapterIcon;
    private int id;
    private int chapterIndex;

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(int updateDate) {
        this.updateDate = updateDate;
    }

    public int getCostCoin() {
        return this.costCoin;
    }

    public void setCostCoin(int costCoin) {
        this.costCoin = costCoin;
    }

    public int getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getChapterName() {
        return this.chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterIcon() {
        return this.chapterIcon;
    }

    public void setChapterIcon(String chapterIcon) {
        this.chapterIcon = chapterIcon;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterIndex() {
        return this.chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.summary);
        dest.writeInt(this.updateDate);
        dest.writeInt(this.costCoin);
        dest.writeInt(this.lastUpdate);
        dest.writeString(this.chapterName);
        dest.writeString(this.chapterIcon);
        dest.writeInt(this.id);
        dest.writeInt(this.chapterIndex);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
