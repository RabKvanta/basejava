package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
        Resume[] storageAll = storage.getAll();
        Assert.assertEquals(new Resume(UUID_1), storageAll[0]);
        Assert.assertEquals(new Resume(UUID_2), storageAll[1]);
        Assert.assertEquals(new Resume(UUID_3), storageAll[2]);
    }

    @Test
    public void save() {

    }

    @Test
    public void delete() {
    }

    @Test(expected = NotExistStorageException.class)
    public void get() {
        storage.get("dummy");
    }
}