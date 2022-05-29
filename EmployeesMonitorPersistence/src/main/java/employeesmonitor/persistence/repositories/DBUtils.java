package employeesmonitor.persistence.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    protected DBUtils() {

    }

    public static Connection getConnection() {
        Properties properties = System.getProperties();
        String dbUrl = properties.getProperty("jdbc.url");
        String password = properties.getProperty("jdbc.password");
        String user = properties.getProperty("jdbc.user");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}