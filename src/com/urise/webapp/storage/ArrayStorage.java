package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        if (r == null) return;
        Integer index = getIndex(r.getUuid());
        if (index == null) {
            System.out.println("Resume is not present!");
            return;
        }
        storage[index] = r;
    }

    public void save(Resume r) {
        if (r == null) return;
        Integer index = getIndex(r.getUuid());
        if (index != null) {
            System.out.println("Resume is already present!");
            return;
        }

        if (size == storage.length) {
            System.out.println("The array is full");
        }
        storage[size++] = r;
    }

    public Resume get(String uuid) {
        Integer index = getIndex(uuid);
        return (index == null ? null : storage[index]);
    }

    public void delete(String uuid) {
        Integer index = getIndex(uuid);
        if (index == null) {
            System.out.println("com.urise.webapp.model.Resume is not present!");
            return;
        }
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}