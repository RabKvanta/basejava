package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    public List<Resume> getListCopy() {
        List<Resume> listCopy = new ArrayList<>();
        Collections.addAll(listCopy, Arrays.copyOfRange(storage, 0, size));
        return listCopy;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    @Override
    protected void doSave(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, (Integer) index);
            size++;
        }
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void doDelete(Object index) {
        fillDeletedElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Object index) {
        return ((Integer) index >= 0);
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract Integer getSearchKey(String uuid);

}
