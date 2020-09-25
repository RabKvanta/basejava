package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class ResumeTestData {

    private static final Map<ContactType, String> CONTACTS_UUID1 = new EnumMap(ContactType.class) {{
        put(ContactType.MOBILE_PHONE, "+38(097) 855-0482");
        put(ContactType.EMAIL, "name1@yandex.ru");
    }};
    private static final Map<ContactType, String> CONTACTS_UUID2 = new EnumMap(ContactType.class) {{
        put(ContactType.MOBILE_PHONE, "+38(095) 155-0482");
        put(ContactType.EMAIL, "name2@yandex.ru");
    }};
    private static final Map<ContactType, String> CONTACTS_UUID3 = new EnumMap(ContactType.class) {{
        put(ContactType.MOBILE_PHONE, "+38(097) 111-1111");
        put(ContactType.EMAIL, "name3@mail.ru");
    }};
    private static final Map<ContactType, String> CONTACTS_UUID4 = new EnumMap(ContactType.class) {{
        put(ContactType.MOBILE_PHONE, "+38(097) 421-1111");
        put(ContactType.EMAIL, "name4@mail.ru");
    }};
    private static final Map<SectionType, AbstractSection> SECTIONS_UUID1 = new EnumMap(SectionType.class) {
        {
            put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок по Java Web"));
            put(SectionType.PERSONAL, new TextSection("Логика, креативность, инициативность."));

            put(SectionType.ACHIEVEMENT, new ListSection(
                    "Успех 1",
                    "Успех 2",
                    "Успех 3"));
            put(SectionType.QUALIFICATION, new ListSection(
                    "Технология 1",
                    "Технология 2",
                    "Технология 3"));
/*
            put(SectionType.EXPERIENCE, new ExperienceSection(
                    new Experience("TOV", "https://www.tutu.ru/", new Experience.Position(2013, Month.OCTOBER, "position2", "content2"),
                            new Experience.Position(2010, Month.APRIL, 2013, Month.SEPTEMBER, "position1", "content1")),
                    new Experience("OOO\"Ku-Ku\"", "https://www.kuku.com/",
                            new Experience.Position(2010, Month.APRIL, "position2", "content2"),
                            new Experience.Position(2009, Month.AUGUST, 2010, Month.MARCH, "position1", "content1"))));
            put(SectionType.EDUCATION, new ExperienceSection(
                    new Experience("KPI", "https://www.kpi.ua/",
                            new Experience.Position(2005, Month.OCTOBER, 2009, Month.MARCH, "Профессор", "Научные изыскания."),
                            new Experience.Position(2004, Month.SEPTEMBER, 2005, Month.MARCH, "Аспирант", "Кафедра.")),
                    new Experience("MGU", "https://www.mgu.com/",
                            new Experience.Position(2000, Month.OCTOBER, 2002, Month.MARCH, "Laborant", "IT facultet"),
                            new Experience.Position(1995, Month.SEPTEMBER, 2000, Month.MARCH, "Student", "IT facultet"))));
*/
        }
    };
    private static final Map<SectionType, AbstractSection> SECTIONS_UUID2 = new EnumMap(SectionType.class) {{
        put(SectionType.OBJECTIVE, new TextSection("Гудвин"));
        put(SectionType.PERSONAL, new TextSection("Великий и ужасный."));

        put(SectionType.ACHIEVEMENT, new ListSection(
                "Победа 1",
                "Победа 2",
                "Победа 3"));
        put(SectionType.QUALIFICATION, new ListSection(
                "Технология 1",
                "Технология 2",
                "Технология 3"));

        put(SectionType.EXPERIENCE, new ExperienceSection(
                new Experience("Изумрудный город", "https://www.gudvin.ru/",
                        new Experience.Position(2013, Month.SEPTEMBER, "position2", "content2"),
                        new Experience.Position(2010, Month.AUGUST, 2013, Month.AUGUST, "position1", "content1")),
                new Experience("OOO\"Totoshka\"", "https://www.totti.com/",
                        new Experience.Position(2010, Month.SEPTEMBER, 2001, Month.AUGUST, "position2", "content2"),
                        new Experience.Position(2001, Month.AUGUST, 2010, Month.AUGUST, "position1", "content2"))));
        put(SectionType.EDUCATION, new ExperienceSection(
                new Experience("Garward", "https://www.garward.com/",
                        new Experience.Position(2000, Month.OCTOBER, 2001, Month.MARCH, "Профессор", "Научные изыскания."),
                        new Experience.Position(1997, Month.SEPTEMBER, 2000, Month.MARCH, "Аспирант", "Кафедра.")),
                new Experience("VUZ", "https://www.vuz.com/",
                        new Experience.Position(1996, Month.OCTOBER, 1997, Month.MARCH, "Laborant", "RTF facultet"),
                        new Experience.Position(1990, Month.SEPTEMBER, 1996, Month.MARCH, "Student", "RTF facultet"))));


    }};


    public static Resume getResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        switch (uuid) {
            case "uuid1":
                resume.setContacts(CONTACTS_UUID1);
                resume.setSections(SECTIONS_UUID1);
                break;
            case "uuid2":
                resume.setContacts(CONTACTS_UUID2);
                resume.setSections(SECTIONS_UUID2);
                break;
            case "uuid3":
                resume.setContacts(CONTACTS_UUID3);
                break;
            case "uuid4":
                resume.setContacts(CONTACTS_UUID4);
                break;
        }
        return resume;
    }

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(ContactType.MOBILE_PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");
        resume.setContacts(contacts);

        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
        sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> list = Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        );
        sections.put(SectionType.ACHIEVEMENT, new ListSection(list));

        list = Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\"");
        sections.put(SectionType.QUALIFICATION, new ListSection(list));

        Experience.Position position = new Experience.Position(LocalDate.of(2013, 10, 1), LocalDate.now(), "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Experience.Position position1 = new Experience.Position(LocalDate.of(2012, 4, 1), LocalDate.of(2014, 4, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        List<Experience> experiences = Arrays.asList(
                new Experience("JAVAOPS", "https://www.javaops.ru/", position),
                new Experience("WRIKE", "https://www.wrike.com/", position1)
        );
        sections.put(SectionType.EXPERIENCE, new ExperienceSection(experiences));
        resume.setSections(sections);

        /* OUTPUT */
        contacts = resume.getContacts();

        for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
            System.out.println(pair.getKey().getTitle() + " : " + pair.getValue());
        }

        sections = resume.getSections();
        for (Map.Entry<SectionType, AbstractSection> pair : sections.entrySet()) {
            System.out.println(pair.getKey().getTitle() + " : " + pair.getValue().toString());
        }
    }

}
