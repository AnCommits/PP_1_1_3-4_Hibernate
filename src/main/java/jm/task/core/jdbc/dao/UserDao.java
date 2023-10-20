package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {

    String TABLE_NAME = "users";

    String ID = "id";
    String NAME = "name";
    String LASTNAME = "lastName";
    String AGE = "age";

    String GET_LAST_USER = String.format(
            "SELECT * FROM %1$s WHERE %2$s = (SELECT MAX(%2$s) from %1$s);",
            TABLE_NAME, ID);

    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    User getLastRecord();
}
