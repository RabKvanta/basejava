package ru.javawebinar.basejava.exception;

import org.postgresql.util.PSQLException;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
    public ExistStorageException(PSQLException e) {
           super(e.getServerErrorMessage().getDetail());
    }
}