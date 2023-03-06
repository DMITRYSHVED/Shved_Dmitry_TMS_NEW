package BD_TASK;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnector {

    public static final String URL = "jdbc:mysql://localhost:3306/students";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "1111";
    private static MySqlConnector instance;

    private MySqlConnector() {
        init();
    }

    protected void init() {
        Logger logger = LogManager.getLogger();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public static MySqlConnector getInstance() {
        if (instance == null) {
            instance = new MySqlConnector();
        }
        return instance;
    }

    protected static Connection getConnection() {
        Logger logger = LogManager.getLogger();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return connection;
    }
}
