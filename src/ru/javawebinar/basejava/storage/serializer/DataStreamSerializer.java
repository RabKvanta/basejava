package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            // Implements sections
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeWithException(sections.entrySet(), dos, entry -> {
                SectionType type = entry.getKey();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(entry.getValue().toString());
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        writeWithException(((ListSection) entry.getValue()).getItems(), dos, dos::writeUTF);
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(((ExperienceSection) entry.getValue()).getExperiences(), dos, ex -> {
                            dos.writeUTF(ex.getHomePage().getName());
                            dos.writeUTF(ex.getHomePage().getUrl());

                            writeWithException(ex.getPositions(), dos, pos -> {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            });
                        });

                        break;
                }
            });

        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            addElementResumeWithException(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            // Implements sections
            addElementResumeWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
            });

            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, FuncWrite<T> funcWrite) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            funcWrite.write(t);
        }
    }

    private <T> List<T> readWithException(DataInputStream dis, FuncRead<T> funcRead) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(funcRead.read());
        }
        return list;
    }

    private void addElementResumeWithException(DataInputStream dis, FuncSection func) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            func.addElement();
        }

    }

    private interface FuncSection {
        void addElement() throws IOException;
    }

    private interface FuncWrite<T> {
        void write(T t) throws IOException;
    }

    private interface FuncRead<T> {
        T read() throws IOException;
    }


    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {

        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());

            case ACHIEVEMENT:
            case QUALIFICATION:
                return new ListSection(readWithException(dis, dis::readUTF));

            case EXPERIENCE:
            case EDUCATION:
                return (new ExperienceSection(readWithException(dis, () -> new Experience(new Link(dis.readUTF(), dis.readUTF()),
                        readWithException(dis, () -> new Experience.Position(
                                LocalDate.parse(dis.readUTF()),
                                LocalDate.parse(dis.readUTF()),
                                dis.readUTF(), dis.readUTF()))))));
            default:
                throw new IllegalStateException();
        }

    }
}
