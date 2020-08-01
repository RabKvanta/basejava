package ru.javawebinar.basejava.model;

import java.util.List;

public class ChronoListSection extends Section {
    private List<ChronoElement> list;

    public ChronoListSection(List<ChronoElement> list) {
        this.list = list;
    }

    public List<ChronoElement> getList() {
        return list;
    }

    public void setList(List<ChronoElement> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "" + list;
    }
}
