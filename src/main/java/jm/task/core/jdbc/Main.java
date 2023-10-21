package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {

    private static final UserService userService = new UserServiceImpl();

    public static UserService getUserService() {
        return userService;
    }

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

//        Создание таблицы User(ов)
        userService.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор.
//        После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных).

        Long id;
        id = userService.saveUserAndGetId("Petr", "Romanov", (byte) 50);
        if (id != null) {
            Util.printSavedUser();
        }

        id = userService.saveUserAndGetId("Bill", "Gates", (byte) 55);
        if (id != null) {
            Util.printSavedUser();
        }

        id = userService.saveUserAndGetId("Misha", "Lomonosov", (byte) 15);
        if (id != null) {
            Util.printSavedUser();
        }

        id = userService.saveUserAndGetId("Casual", "Passerby", (byte) 100);
        if (id != null) {
            Util.printSavedUser();
        }

        // Удаляем юзера (возможно несуществующего)
        id = 3L;
        userService.removeUserById(id);

        // Удаляем несуществующего юзера
        userService.removeUserById(id);

        System.out.println("==================================");

//        Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(System.out::println);

        System.out.println("==================================");
//        Очистка таблицы User(ов)
        userService.cleanUsersTable();

        // Убеждаемся, что таблица пустая
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("==================================");

//        Удаление таблицы
        userService.dropUsersTable();

//        Util.closeSessionFactory();
        Util.closeConnection();
    }
}
