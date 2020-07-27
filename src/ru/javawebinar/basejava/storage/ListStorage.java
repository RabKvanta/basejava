package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> getListCopy() {
        return new ArrayList<>(list);
    }

    @Override
    protected void doUpdate(Resume r, Integer index) {
        list.add(index, r);
    }

    @Override
    protected void doSave(Resume r, Integer index) {
        list.add(r);
    }

    @Override
    protected Resume doGet(Integer index) {
        return list.get(index);
    }

    @Override
    protected void doDelete(Integer index) {
        list.remove(index.intValue());
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).getUuid().equals(uuid)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer index) {
        return (index >= 0);
    }
}