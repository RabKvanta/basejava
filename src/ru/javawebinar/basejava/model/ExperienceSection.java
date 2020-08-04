package ru.javawebinar.basejava.model;

import java.util.List;

public class ExperienceSection extends AbstractSection {
    private List<Experience> list;

    public ExperienceSection(List<Experience> list) {
        this.list = list;
    }

    public List<Experience> getList() {
        return list;
    }

    public void setList(List<Experience> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "" + list;
    }
}
