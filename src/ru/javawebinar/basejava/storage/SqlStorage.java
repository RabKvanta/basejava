package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        // Register JDBC driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {

        return sqlHelper.transactionalExecute(conn -> {
                    Resume r;
                    try (PreparedStatement ps = conn.prepareStatement("SELECT full_name FROM resume  WHERE uuid =?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        r = new Resume(uuid, rs.getString(1));
                    }

                    getItemsResume(conn, "SELECT * FROM contact WHERE resume_uuid =?", uuid, rs -> addContact(rs, r));
                    getItemsResume(conn, "SELECT * FROM section WHERE resume_uuid =?", uuid, rs -> addSection(rs, r));
                    return r;
                }
        );
    }

    @Override
    public void update(Resume r) {

        sqlHelper.transactionalExecute(conn -> {
                    String uuid = r.getUuid();
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE  resume  SET  full_name=? WHERE uuid =?")) {
                        ps.setString(2, uuid);
                        ps.setString(1, r.getFullName());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(uuid);
                        }
                    }
                    deleteRows(conn, "DELETE  FROM contact  WHERE resume_uuid = ? ", uuid);
                    insertContact(conn, r);

                    deleteRows(conn, "DELETE  FROM section  WHERE resume_uuid = ? ", uuid);
                    insertSection(conn, r);

                    return null;
                }
        );

    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });

    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE  FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("    SELECT * FROM resume  " +
                    "  ORDER BY full_name,uuid")) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.putIfAbsent(uuid, new Resume(uuid, rs.getString("full_name")));
                }

            }

            getItemsResume(conn, "SELECT * FROM contact", rs -> addContact(rs, resumes.get(rs.getString("resume_uuid"))));

            getItemsResume(conn, "SELECT * FROM section", rs -> addSection(rs, resumes.get(rs.getString("resume_uuid"))));

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                SectionType type = e.getKey();
                ps.setString(2, type.name());
                String content = "";
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        content = e.getValue().toString();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        content = String.join("\n", ((ListSection) e.getValue()).getItems());
                        break;
                }
                ps.setString(3, content);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void getItemsResume(Connection conn, String sqlRqst, String uuid, AddingItem addItem) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlRqst)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addItem.execute(rs);
            }
        }
    }

    private void getItemsResume(Connection conn, String sqlRqst, AddingItem addItem) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlRqst)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addItem.execute(rs);
            }
        }
    }

    private interface AddingItem {
        void execute(ResultSet rs) throws SQLException;
    }

    private void deleteRows(Connection conn, String sqlRqst, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlRqst)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }


    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {

            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(type, new TextSection(content));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    List<String> strings = Arrays.asList(content.split("\n"));
                    r.addSection(type, new ListSection(strings));
                    break;
            }
        }
    }
}
