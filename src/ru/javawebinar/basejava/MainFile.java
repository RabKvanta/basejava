package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printAllFiles(dir);
    }

    public static void printAllFiles(File dir) {
        if (!dir.isDirectory()) {
            System.out.println(dir.getName());
            return;
        } else {
            System.out.println("Dir: " + dir.getName().toUpperCase());
        }

        File[] listFiles = dir.listFiles();

        if (listFiles != null) {
            for (File file : listFiles) {
                if (!file.isDirectory()) {
                    System.out.println("--" + file.getName());
                }
            }

            for (File file : listFiles) {
                if (file.isDirectory()) {
                    printAllFiles(file);
                }
            }
        }
    }
}