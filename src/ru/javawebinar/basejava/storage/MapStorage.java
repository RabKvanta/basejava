package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listCopy = new ArrayList<>(map.values());
        Collections.sort(listCopy);
        return listCopy;
    }

   /* public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }*/

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        Resume mapKey = (Resume) searchKey;
        map.put(mapKey.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        Resume mapKey = (Resume) searchKey;
        map.put(mapKey.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        Resume mapKey = (Resume) searchKey;
        return map.get(mapKey.getUuid());
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume mapKey = (Resume) searchKey;
        map.remove(mapKey.getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume mapKey = map.get(uuid);
        if (mapKey == null)
            mapKey = new Resume(uuid,"");
        return mapKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        Resume mapKey = (Resume) searchKey;
        return map.containsKey(mapKey.getUuid());
    }
}
