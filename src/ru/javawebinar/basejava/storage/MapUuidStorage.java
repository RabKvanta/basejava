package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    protected Map<String, Resume> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getListCopy() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        map.put((String) key, r);
    }

    @Override
    protected void doSave(Resume r, Object key) {
        map.put((String) key, r);
    }

    @Override
    protected Resume doGet(Object key) {
        return map.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        map.remove(key);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object key) {
        return map.containsKey(key);
    }
}
