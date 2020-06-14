package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    protected List<Resume> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    protected void updateElement(Resume r, Object index) {
        list.add((int) index, r);
    }

    @Override
    protected void saveElement(Resume r, Object index) {
        list.add(r);
    }

    @Override
    protected Resume getElement(Object index) {
        return list.get((int) index);
    }

    @Override
    protected void deleteElement(Object index) {
        list.remove((int) index);
    }

    @Override
    protected Object getKey(String uuid) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).getUuid().equals(uuid)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object index) {
        return ((int) index >= 0);
    }
}