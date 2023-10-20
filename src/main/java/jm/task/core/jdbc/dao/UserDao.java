package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {

    String TABLE_NAME = "users";

    String ID = "id";
    String NAME = "name";
    String LASTNAME = "lastName";
    String AGE = "age";

    String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(" +
                    ID + " BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    NAME + " VARCHAR(30)," +
                    LASTNAME + " VARCHAR(30)," +
                    AGE + " TINYINT" +
                    ")";

    String DELETE_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);

    String DELETE_ALL_ENTRIES = String.format("DELETE FROM %s;", TABLE_NAME);

    String GET_LAST_USER = String.format(
            "SELECT * FROM %1$s WHERE %2$s = (SELECT MAX(%2$s) from %1$s);",
            TABLE_NAME, ID);

    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    // Returns the last user from DB
    User getLastRecord();
}
