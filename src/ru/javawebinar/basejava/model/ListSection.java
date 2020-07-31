package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {

    private List<String> list = new ArrayList<>();

    public void setList(List<String> list) {
        this.list = list;
    }
}
