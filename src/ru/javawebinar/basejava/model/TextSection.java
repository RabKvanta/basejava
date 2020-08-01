package ru.javawebinar.basejava.model;

public class TextSection extends Section {

    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "" + content;
    }
}
