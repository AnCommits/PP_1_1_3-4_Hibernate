package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    private void execute(String sql) {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createUsersTable() {
        execute(CREATE_TABLE);
    }

    @Override
    public void dropUsersTable() {
        execute(DELETE_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        final String SAVE_USER = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                TABLE_NAME, NAME, LASTNAME, AGE);
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        final String REMOVE_BY_ID = String.format("DELETE FROM %s WHERE id = %d;", TABLE_NAME, id);
        execute(REMOVE_BY_ID);
    }

    @Override
    public List<User> getAllUsers() {
        final String GET_ALL_USERS = String.format("SELECT * FROM %s;", TABLE_NAME);

        List<User> listOfAllUsers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User(resultSet.getString(NAME),
                        resultSet.getString(LASTNAME),
                        resultSet.getByte(AGE));
                user.setId(resultSet.getLong(ID));
                listOfAllUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfAllUsers;
    }

    @Override
    public void cleanUsersTable() {
        String DELETE_ALL_ENTRIES = String.format("DELETE FROM %s", TABLE_NAME);
        execute(DELETE_ALL_ENTRIES);
    }

    @Override
    public User getLastRecord() {
        String GET_LAST_USER = String.format(
                "SELECT * FROM %1$s WHERE %2$s = (SELECT MAX(%2$s) from %1$s);",
                TABLE_NAME, ID);
        User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_LAST_USER);
            if (resultSet.next()) {
                user = new User(resultSet.getString(NAME),
                        resultSet.getString(LASTNAME),
                        resultSet.getByte(AGE));
                user.setId(resultSet.getLong(ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
