package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> listStrings = ((ListSection) entry.getValue()).getItems();
                        dos.writeInt(listStrings.size());
                        for (String s : listStrings) {
                            dos.writeUTF(s);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Experience> experiences = ((ExperienceSection) entry.getValue()).getExperiences();
                        dos.writeInt(experiences.size());
                        for (Experience ex : experiences) {
                            dos.writeUTF(ex.getHomePage().getName());
                            dos.writeUTF(ex.getHomePage().getUrl());
                            List<Experience.Position> positions = ex.getPositions();
                            dos.writeInt(positions.size());

                            for (Experience.Position pos : positions) {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            }
                        }
                        break;
                }
            }
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
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> listStrings = new ArrayList<>();
                        int sizeList = dis.readInt();
                        for (int k = 0; k < sizeList; k++) {
                            listStrings.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(listStrings));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Experience> experiences = new ArrayList<>();
                        int sizeEx = dis.readInt();
                        for (int k = 0; k < sizeEx; k++) {
                            String linkName = dis.readUTF();
                            String linkUrl = dis.readUTF();
                            int sizePos = dis.readInt();
                            List<Experience.Position> positions = new ArrayList<>();
                            for (int j = 0; j < sizePos; j++) {
                                positions.add(new Experience.Position(
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        dis.readUTF(), dis.readUTF()));
                            }
                            experiences.add(new Experience(new Link(linkName, linkUrl), positions));
                        }
                        resume.addSection(sectionType, new ExperienceSection(experiences));
                        break;

                }
            }

            return resume;
        }
    }
}
