package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserService {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    // Returns the last user from DB
    User getLastRecord();

    // Saves user into DB and returns user's id
    Long saveUserAndGetId(String name, String lastName, byte age);
}
