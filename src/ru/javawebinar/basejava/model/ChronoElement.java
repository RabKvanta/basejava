package ru.javawebinar.basejava.model;

import java.util.Date;

public class ChronoElement {
    private Date startDate;
    private Date endDate;
    private String title;
    private String link;
    private String content;

    public ChronoElement(Date startDate, Date endDate, String title, String link, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.link = link;
        this.content = content;
    }

    public ChronoElement(Date startDate, Date endDate, String title, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "\n" + startDate + " - " + endDate + " " +
                title + " " + (link == null ? "" : link) + " " + content;
    }
}
