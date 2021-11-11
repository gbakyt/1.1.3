package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException, ClassNotFoundException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Gaziz", "Bakyt", (byte) 23);
        userService.saveUser("Kymbat", "Seidulla", (byte) 20);
        userService.saveUser("Aibek", "Batyrkhan", (byte) 24);
        userService.saveUser("Aiken", "Sultan", (byte) 25);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
