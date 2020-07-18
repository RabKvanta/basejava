package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
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
    protected void doUpdate(Resume r, Object searchKey) {
        map.put(((Resume) searchKey).getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        map.put(((Resume) searchKey).getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(((Resume) searchKey).getUuid());
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = map.get(uuid);
        if (searchKey == null)
            searchKey = new Resume(uuid, "");
        return searchKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(((Resume) searchKey).getUuid());
    }
}
