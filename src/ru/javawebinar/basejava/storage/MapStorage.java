package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends ArrayStorage {
    protected Map<String, Resume> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    protected void updateElement(Resume r, Object index) {
        map.put((String) index, r);
    }

    @Override
    protected void saveElement(Resume r, Object index) {
        map.put((String) index, r);
    }

    @Override
    protected Resume getElement(Object index) {
        return map.get((String) index);
    }

    @Override
    protected void deleteElement(Object index) {
        map.remove((String) index);
    }

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected void checkNotExist(Object index, String uuid) {
        if (!map.containsKey(uuid)) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected void checkExist(Object index, String uuid) {
        if (map.containsKey(uuid)) {
            throw new ExistStorageException(uuid);
        }
    }
}
