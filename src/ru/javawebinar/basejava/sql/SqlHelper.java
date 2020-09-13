package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sqlCommand, SqlProcess<T> sqlProcess) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            T returnValue = sqlProcess.execute(ps);
            return returnValue;

        } catch (SQLException e) {
            //  System.out.println("sqlCommand " + e.getSQLState()+" "+e.getErrorCode());

            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException((PSQLException) e);
            } else throw new StorageException(e);
        }
    }
}
