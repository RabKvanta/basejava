package ru.javawebinar.basejava.model;

public enum ContactType {
    MOBILE_PHONE("Моб.тел."),
    HOME_PHONE("Домашний тел."),
    EMAIL("Почта"),
    SKYPE("Skype"),
    GITHUB("GitHub"),
    LINKEDIN("LinkedIn"),
    STACKOVERFLOW("Stackovrflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
