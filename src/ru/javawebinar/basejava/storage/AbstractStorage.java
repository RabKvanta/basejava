package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object index = getIndex(r.getUuid());
        checkNotExist(index, r.getUuid());
        updateElement(r, index);
    }

    public void save(Resume r) {
        Object index = getIndex(r.getUuid());
        checkExist(index, r.getUuid());
        saveElement(r, index);
    }

    public void delete(String uuid) {
        Object index = getIndex(uuid);
        checkNotExist(index, uuid);
        deleteElement(index);
    }

    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        checkNotExist(index, uuid);
        return getElement(index);
    }

    protected abstract void updateElement(Resume r, Object index);

    protected abstract void saveElement(Resume r, Object index);

    protected abstract Resume getElement(Object index);

    protected abstract void deleteElement(Object index);

    protected abstract Object getIndex(String uuid);

    protected abstract void checkNotExist(Object index, String uuid);

    protected abstract void checkExist(Object index, String uuid);
}
