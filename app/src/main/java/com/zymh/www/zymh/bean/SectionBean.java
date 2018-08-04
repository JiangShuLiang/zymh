package com.zymh.www.zymh.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class SectionBean extends SectionEntity<HomePageDataBodyBooks> {
    public boolean showMore;
    public String sectionId;
    public String header;

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(boolean isHeader, String header, boolean showMore) {
        super(isHeader, header);
        this.showMore = showMore;
    }

    public SectionBean(boolean isHeader, String header, String sectionId, boolean showMore) {
        super(isHeader, header);
        this.header = header;
        this.showMore = showMore;
        this.sectionId = sectionId;
    }

    public SectionBean(HomePageDataBodyBooks data) {
        super(data);
    }

    public boolean isShowMore() {
        return showMore;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getHeader() {
        return header;
    }
}
