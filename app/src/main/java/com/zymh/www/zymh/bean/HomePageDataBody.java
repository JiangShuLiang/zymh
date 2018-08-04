package com.zymh.www.zymh.bean;

public class HomePageDataBody {
    private HomePageDataBodyBooks[] books;
    private boolean more;
    private int sectionId;
    private String title;

    public HomePageDataBodyBooks[] getBooks() {
        return this.books;
    }

    public void setBooks(HomePageDataBodyBooks[] books) {
        this.books = books;
    }

    public boolean getMore() {
        return this.more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public int getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
