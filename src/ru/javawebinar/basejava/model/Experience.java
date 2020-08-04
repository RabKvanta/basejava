package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Experience {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String link;
    private String content;

    public Experience(LocalDate startDate, LocalDate endDate, String title, String link, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.link = link;
        this.content = content;
    }

    public Experience(LocalDate startDate, LocalDate endDate, String title, String content) {
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
