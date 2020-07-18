package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            if (o1.getFullName().equals(o2.getFullName()))
                return o1.getUuid().compareTo(o2.getUuid());
            else return o1.getFullName().compareTo(o2.getFullName());
        }
    };

    public List<Resume> getAllSorted() {
        List<Resume> listCopy = getListCopy();
        Collections.sort(listCopy, RESUME_COMPARATOR);
        return listCopy;
    }

    public void update(Resume r) {
        Object searchKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public void save(Resume r) {
        Object searchKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void doUpdate(Resume r, Object key);

    protected abstract void doSave(Resume r, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object key);

    protected abstract List<Resume> getListCopy();
}
