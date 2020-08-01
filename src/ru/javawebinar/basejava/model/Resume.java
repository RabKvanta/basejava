package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private  Map<SectionType, Section> sections = new HashMap<>();
    private  Map<ContactType, String> contacts = new HashMap<>();


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setSections(Map<SectionType, Section> sections) {
        this.sections = sections;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }
}
