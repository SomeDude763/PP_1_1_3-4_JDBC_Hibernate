package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
   private final Connection connection = getConnection();
    private static String sql = "" ;


    public UserDaoJDBCImpl() throws SQLException {
    }

    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS users(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,name VARCHAR(100) NOT NULL,last_name VARCHAR(100) " +
                "NOT NULL,age TINYINT NOT NULL)" ;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users" ;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO users(name,last_name,age) VALUES(?,?,?)" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        sql = "DELETE FROM users WHERE id = ?" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        sql = "SELECT id,name,last_name,age FROM users" ;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println("User с именем - " + user.getName() + " добавлен в базу данных" );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;

    }

    public void cleanUsersTable() {
        sql = "TRUNCATE TABLE users";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
