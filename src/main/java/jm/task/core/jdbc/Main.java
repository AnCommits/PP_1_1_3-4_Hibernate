package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;
import java.util.function.BiConsumer;

public class Main {

    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

//        Создание таблицы User(ов)
        userService.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор.
//        После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных).

        BiConsumer<User, User> printSavingUser = (u1, u2) -> {
            if (u2 != null && (u1 == null || !u1.getId().equals(u2.getId()))) {
                System.out.printf("User с именем – %s %s добавлен в базу данных%n",
                        u2.getName(), u2.getLastName());
            }
        };

        User lastUserBeforeSaving;
        User lastUserAfterSaving;

        lastUserBeforeSaving = userService.getLastRecord();
        userService.saveUser("Petr", "Romanov", (byte) 50);
        lastUserAfterSaving = userService.getLastRecord();
        printSavingUser.accept(lastUserBeforeSaving, lastUserAfterSaving);

        lastUserBeforeSaving = userService.getLastRecord();
        userService.saveUser("Bill", "Gates", (byte) 55);
        lastUserAfterSaving = userService.getLastRecord();
        printSavingUser.accept(lastUserBeforeSaving, lastUserAfterSaving);

        lastUserBeforeSaving = userService.getLastRecord();
        userService.saveUser("Misha", "Lomonosov", (byte) 15);
        lastUserAfterSaving = userService.getLastRecord();
        printSavingUser.accept(lastUserBeforeSaving, lastUserAfterSaving);

        lastUserBeforeSaving = userService.getLastRecord();
        userService.saveUser("Casual", "Passerby", (byte) 100);
        lastUserAfterSaving = userService.getLastRecord();
        printSavingUser.accept(lastUserBeforeSaving, lastUserAfterSaving);

        userService.removeUserById(2);
        userService.removeUserById(2);

        System.out.println("==================================");

//        Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(System.out::println);

        System.out.println("==================================");
//        Очистка таблицы User(ов)
        userService.cleanUsersTable();

        userService.getAllUsers().forEach(System.out::println);
        System.out.println("==================================");

//        Удаление таблицы
        userService.dropUsersTable();

        Util.closeSessionFactory();
//        Util.closeConnection();
    }
}

