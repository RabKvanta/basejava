package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.TextSection;

public class JsonParserTest  {
    private static final String UUID_1 = "uuid1"; // = UUID.randomUUID().toString();
    private static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1, "Name1");

    private static final String UUID_2 = "uuid2"; // = UUID.randomUUID().toString();
    private static final Resume RESUME_2 = ResumeTestData.getResume(UUID_2, "Name2");

    private static final String UUID_3 = "uuid3"; // = UUID.randomUUID().toString();
    private static final Resume RESUME_3 = ResumeTestData.getResume(UUID_3, "Name3");

    private static final String UUID_4 = "uuid4"; // = UUID.randomUUID().toString();
    private static final Resume RESUME_4 = ResumeTestData.getResume(UUID_4, "Name4");

    @Test
    public void testResume(){
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1,resume);
    }

    @Test
    public void read() {
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextSection("Objective1");
        String json =JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1,section2);
    }
}