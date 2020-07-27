package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(ArrayStorage.class.getName());

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public List<Resume> getAllSorted() {
        List<Resume> listResumes = getListCopy();
        listResumes.sort(RESUME_COMPARATOR);
        return listResumes;
    }

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void doUpdate(Resume r, SK key);

    protected abstract void doSave(Resume r, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK key);

    protected abstract List<Resume> getListCopy();
}
