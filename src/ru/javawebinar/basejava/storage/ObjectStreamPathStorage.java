package ru.javawebinar.basejava.storage;

import java.nio.file.Path;

public class ObjectStreamPathStorage extends ObjectStreamStorage {

    protected ObjectStreamPathStorage(Path directory) {
        super(directory.toFile());
    }


}
