package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {

    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

// В задании не определен алгоритм действий при создании таблицы
// на случай, если таблица с нужным именем в БД уже существует.
// Что делать, если существующая таблица содержит нужные колонки? Удалить/оставить/запросить подтверждение удаления
// И что делать, если существующая таблица не содержит нужные колонки?

        List<User> allUsers;

//        Создание таблицы User(ов)
        userService.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор.
//        После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных).

// Если вывод в консоль в методе main,
// то как узнать, успешно ли добавился юзер в БД?
// Если использовать только методы UserService, то полезным может быть только getAllUsers
// Например, сравнить количество юзеров в таблице до и после добавления юзера в таблицу.
// И ради этого 2 раза получать из базы список всех юзеров?

// Как сказал ментор, всех юзеров выводить в консоль после дабавления всех в БД
// Значит, задание сформулировано некорректно (возможно сознательно).

        userService.saveUser("Petr", "Romanov", (byte) 50);

        userService.saveUser("Bill", "Gates", (byte) 55);

        userService.saveUser("Misha", "Lomonosov", (byte) 15);

        userService.saveUser("Casual", "Passerby", (byte) 100);

//        Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        allUsers = userService.getAllUsers();
        allUsers.forEach(System.out::println);

//        Очистка таблицы User(ов)
        userService.cleanUsersTable();

//        Удаление таблицы
        userService.dropUsersTable();

        Util.closeSessionFactory();
//        Util.closeConnection();
    }
}

// show tables;
// DROP TABLE IF EXISTS users;
// CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(30), age TINYINT);
// Select * FROM users;
