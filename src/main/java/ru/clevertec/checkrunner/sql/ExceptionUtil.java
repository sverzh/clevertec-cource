package ru.clevertec.checkrunner.sql;

import org.postgresql.util.PSQLException;
import ru.clevertec.checkrunner.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {

            if (e.getSQLState().equals("23505")) {
                return new StorageException("Duplicate key value",e);
            }
        }
        return new StorageException(e);
    }
}
