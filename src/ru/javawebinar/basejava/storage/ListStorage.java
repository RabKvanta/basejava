package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    protected List<Resume> list = new ArrayList<>();

    public void clear() {
        list.clear();
        size = 0;
    }

    public Resume[] getAll() {
        return list.toArray(new Resume[size]);
    }

    @Override
    protected void updateElement(Resume r, int index) {
        list.add(index, r);
    }

    @Override
    protected void saveElement(Resume r, int index) {
        list.add(r);
        size++;
    }

    @Override
    protected Resume getElement(int index) {
        return list.get(index);
    }

    @Override
    protected void deleteElement(int index) {
        list.remove(index);
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        int index = 0;
        for (Resume r : list) {
            if (r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}