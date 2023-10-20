package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    private void execute(String sql) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql, User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        "(" +
                        ID + " BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                        NAME + " VARCHAR(30), " +
                        LASTNAME + " VARCHAR(30), " +
                        AGE + " TINYINT" +
                        ")";
        execute(CREATE_TABLE);
    }

    @Override
    public void dropUsersTable() {
        final String DELETE_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        execute(DELETE_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        final String DELETE_ALL_ENTRIES = String.format("DELETE FROM %s;", TABLE_NAME);
        execute(DELETE_ALL_ENTRIES);
    }

    @Override
    public User getLastRecord() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createNativeQuery(GET_LAST_USER, User.class).list();
            if (!users.isEmpty()) {
                return users.get(0);
            }
        }
        return null;
    }
}
