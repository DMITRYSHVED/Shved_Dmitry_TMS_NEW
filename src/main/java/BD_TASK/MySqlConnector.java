package BD_TASK;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnector {

    public static final String URL = "jdbc:mysql://localhost:3306/students";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "1111";

    protected static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            //log
        }
        return connection;
    }
}
