package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
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
        return list.toArray(new Resume[list.size()]);
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
    protected Object getIndex(String uuid) {
        int index = 0;
        for (Resume r : list) {
            if (r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected void checkNotExist(Object index, String uuid) {
        if ((int) index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected void checkExist(Object index, String uuid) {
        if ((int) index >= 0) {
            throw new ExistStorageException(uuid);
        }
    }

}