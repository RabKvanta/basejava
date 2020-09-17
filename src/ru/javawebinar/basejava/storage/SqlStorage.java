package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        if (rs.getString("resume_uuid") != null) {
                            addContact(rs, r);
                        }
                    } while (rs.next());
                    return r;

                });
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
                    try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM contact  WHERE resume_uuid =?")) {
                        ps.setString(1, uuid);
                        ps.execute();
                    }
                    insertContact(conn, r);
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
        return sqlHelper.execute("    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "  ORDER BY full_name,uuid",
                ps -> {
                    Map<String, Resume> mapResumes = new LinkedHashMap<>();

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        Resume r = mapResumes.get(uuid);
                        if (r == null) {
                            r = new Resume(uuid, rs.getString("full_name"));
                        }
                        addContact(rs, r);
                        mapResumes.put(uuid, r);
                    }
                    return new ArrayList<>(mapResumes.values());
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

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        if (rs.getString("resume_uuid") != null) {
            String value = rs.getString("value");
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }
}
