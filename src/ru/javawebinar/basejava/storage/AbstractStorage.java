package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object key = checkNotExist(r.getUuid());
        updateElement(r, key);
    }

    public void save(Resume r) {
        Object key = checkExist(r.getUuid());
        saveElement(r, key);
    }

    public void delete(String uuid) {
        Object key = checkNotExist(uuid);
        deleteElement(key);
    }

    public Resume get(String uuid) {
        Object key = checkNotExist(uuid);
        return getElement(key);
    }

    protected Object checkNotExist(String uuid) {
        Object key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected Object checkExist(String uuid) {
        Object key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void updateElement(Resume r, Object key);

    protected abstract void saveElement(Resume r, Object key);

    protected abstract Resume getElement(Object key);

    protected abstract void deleteElement(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract boolean isExist(Object key);
}
