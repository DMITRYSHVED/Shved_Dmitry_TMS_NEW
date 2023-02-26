package BD_TASK;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Scanner;

public class StudentsManager {

    private static Logger logger = Logger.getLogger(StudentsManager.class);
    private static Connection connection;
    private static Scanner scanner;

    protected static boolean createTables() {
        connection = MySqlConnector.getConnection();
        Statement studentsTableStatement = null;

        try {
            studentsTableStatement = connection.createStatement();
            studentsTableStatement.execute
                    (
                            "CREATE TABLE IF NOT EXISTS students (" +
                                    "  id INT NOT NULL AUTO_INCREMENT," +
                                    "  name VARCHAR(45) NULL," +
                                    "  surname VARCHAR(45) NULL," +
                                    "  town VARCHAR(45) NULL," +
                                    "  PRIMARY KEY (id))"
                    );
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            try {
                if (studentsTableStatement != null) {
                    studentsTableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return true;
    }

    protected static boolean addStudent(Student student) {
        connection = MySqlConnector.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO students (name,surname,town) values (?,?,?)");
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getTown().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return true;
    }

    protected static boolean deleteStudent() {
        scanner = new Scanner(System.in);
        connection = MySqlConnector.getConnection();
        int deleteId;
        PreparedStatement preparedStatement = null;

        System.out.println("ENTER THE ID OF THE STUDENT TO BE DELETED:");
        while (!scanner.hasNextInt()) {
            logger.warn("USER ENTERED INVALID FORMAT");
            System.out.println("We need a number, try again:");
            scanner.nextLine();
        }
        deleteId = scanner.nextInt();
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
            preparedStatement.setInt(1, deleteId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return true;
    }

    protected static Student createStudent() {
        Student student = new Student();
        Town town = new Town();
        scanner = new Scanner(System.in);

        System.out.println("ENTER STUDENT NAME:");
        student.setName(scanner.nextLine());
        System.out.println("ENTER STUDENT SURNAME:");
        student.setSurname(scanner.nextLine());
        System.out.println("ENTER STUDENT TOWN-NAME:");
        town.setName(scanner.nextLine());
        student.setTown(town);
        return student;
    }

    protected static void showInfo() {
        connection = MySqlConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM students");
            System.out.println("+----+------------+------------+-------------+");
            System.out.println("| id | name       | surname    | town        |");
            System.out.println("+----+------------+------------+-------------+");
            while (resultSet.next()) {
                System.out.printf("|%-1s%-3d| %-11s| %-11s| %-12s|\n", "", resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getString("surname"),
                        resultSet.getString("town"));
            }
            System.out.println("+----+------------+------------+-------------+");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    protected static int getFunction() {
        scanner = new Scanner(System.in);
        int fun;

        System.out.println("1- ADD STUDENT 2- DELETE STUDENT\n0- exit");
        while (!scanner.hasNextInt()) {
            logger.warn("USER ENTERED INVALID FORMAT");
            System.err.println("We need a number, try again:");
            scanner.nextLine();
        }
        fun = scanner.nextInt();
        switch (fun) {
            case 1:
                logger.info("USER IS ADDING A STUDENT");
                if (addStudent(createStudent())) {
                    logger.info("SUCCESSFULLY");
                }
                return 1;
            case 2:
                logger.info("USER IS DELETING A STUDENT");
                if (deleteStudent()) {
                    logger.info("SUCCESSFULLY");
                }
                return 2;
            case 0:
                return 0;
        }
        return -1;
    }

    public static void showMenu() {
        logger.info("USER CONNECTED");
        if (createTables()) {
            logger.info("TABLES CREATED SUCCESSFULLY");
        }
        while (true) {
            showInfo();
            if (getFunction() == 0) {
                break;
            }
        }
        logger.info("USER DISCONNECTED");
    }
}
