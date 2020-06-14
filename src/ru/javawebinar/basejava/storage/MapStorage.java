package ru.javawebinar.basejava.storage;

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
    protected void updateElement(Resume r, Object key) {
        map.put((String) key, r);
    }

    @Override
    protected void saveElement(Resume r, Object key) {
        map.put((String) key, r);
    }

    @Override
    protected Resume getElement(Object key) {
        return map.get(key);
    }

    @Override
    protected void deleteElement(Object key) {
        map.remove(key);
    }

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object key) {
        return map.containsKey(key);
    }
}
