package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ChronoListSection extends Section {
    private List<ItemDate> list = new ArrayList<>();

    public List<ItemDate> getList() {
        return list;
    }

    public void setList(List<ItemDate> list) {
        this.list = list;
    }
}
