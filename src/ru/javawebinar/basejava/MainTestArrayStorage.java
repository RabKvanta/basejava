package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test ru.javawebinar.basejava.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = Config.get().getStorage();//new SortedArrayStorage();


    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Cutkin Tit Tot");

        Resume r2 = new Resume("uuid2", "Shmakin Savel Savelich");

        Resume r3 = new Resume("uuid3", "Olimpuk Svet Bazovich");

        Resume r4 = new Resume("uuid4", "Hrenakin Evgraf Vsevolodovish");

        Resume r5 = new Resume("uuid5", "Hrenakin Evgraf Vsevolodovish");
        Resume r = ARRAY_STORAGE.get("uuid1");

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r5);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        //  System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        //  System.out.println("Index of r2: " + Arrays.binarySearch(ARRAY_STORAGE.getAll(), 0, ARRAY_STORAGE.size(), r2));
        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.update(r3);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r + " " + r.getFullName());
        }
    }
}
