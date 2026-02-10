package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase" ;
    private static final String USERNAME = "root" ;
    private static final String PASSWORD = "root" ;

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Con ok");
        } catch (SQLException e) {
            System.out.println("Con error");
        }
        return connection;
    }
}
