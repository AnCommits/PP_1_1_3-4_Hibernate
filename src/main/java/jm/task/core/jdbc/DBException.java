package jm.task.core.jdbc;

import java.sql.SQLException;

public class DBException extends SQLException {

    public DBException(String message) {
        super(message);
    }
}
