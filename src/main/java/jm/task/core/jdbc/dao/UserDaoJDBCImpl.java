package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement pStatement = connection
                    .prepareStatement("CREATE TABLE IF NOT EXISTS users " +
                            "(id BIGINT not NULL AUTO_INCREMENT, " +
                            "name VARCHAR(80) not NULL, " +
                            " lastName varchar(80) not NULL," +
                            " age tinyint not NULL, " +
                            " PRIMARY KEY ( id ))");
            if (pStatement != null) {
                pStatement.executeUpdate();
                System.out.println("Таблица успешно создана");
            }
        } catch (SQLException sqlException) {
            System.out.println("Таблицу не удалось создать");
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement pStatement =
                     Util.getConnection()
                    .prepareStatement("DROP TABLE IF EXISTS users")) {
            if (pStatement != null) {
                pStatement.executeUpdate();
                System.out.println("Таблица успешно удалена");
            }
        } catch (SQLException sqlException) {
            System.out.println("Таблицу удалить не удалось");
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pStatement =
                     Util.getConnection().prepareStatement
                             ("insert into users (age, name, lastName) VALUES(?,?,?)")) {
            pStatement.setByte(1, age);
            pStatement.setString(2, name);
            pStatement.setString(3, lastName);
            pStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException sqlException) {
            System.out.println("User не был добавлен в базу данных");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement pStatement = connection
                    .prepareStatement("delete from  users where id=?");
            pStatement.setLong(1, id);
            pStatement.executeUpdate();
            System.out.println("Удаление прошло успешно");
        } catch (SQLException sqlException) {
            System.out.println("Удалить User-а не получилось");
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            PreparedStatement pStatement = connection
                    .prepareStatement("SELECT id, name, lastName,age from users");
            ResultSet rSet = pStatement.executeQuery();
            while (rSet.next()) {
                User user = new User();
                user.setId(rSet.getLong("id"));
                user.setName(rSet.getString("name"));
                user.setLastName(rSet.getString("lastName"));
                user.setAge(rSet.getByte("age"));

                listOfUsers.add(user);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getStackTrace());
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement pStatement = connection.prepareStatement("TRUNCATE users");
            if (pStatement != null) {
                pStatement.executeUpdate();
                System.out.println("Таблица очищена");
            }
        } catch (SQLException sqlException) {
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
