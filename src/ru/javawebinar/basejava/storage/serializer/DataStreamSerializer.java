package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // Implements sections
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            if (sections.size() == 0)
                return;
            dos.writeUTF(SectionType.OBJECTIVE.name());
            dos.writeUTF((sections.get(SectionType.OBJECTIVE)).toString());
            dos.writeUTF(SectionType.PERSONAL.name());
            dos.writeUTF((sections.get(SectionType.PERSONAL)).toString());
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            // Implements sections
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String sectionType = dis.readUTF();
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        resume.addSection(SectionType.valueOf(sectionType), new TextSection(dis.readUTF()));
                        break;
                }
            }

            return resume;
        }
    }
}
