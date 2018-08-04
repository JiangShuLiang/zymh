package com.zymh.www.zymh.bean;

public class ComicPagesData {
    private ComicPagesDataChapter chapter;
    private ComicPagesDataPages[] pages;

    public ComicPagesDataChapter getChapter() {
        return this.chapter;
    }

    public void setChapter(ComicPagesDataChapter chapter) {
        this.chapter = chapter;
    }

    public ComicPagesDataPages[] getPages() {
        return this.pages;
    }

    public void setPages(ComicPagesDataPages[] pages) {
        this.pages = pages;
    }
}
