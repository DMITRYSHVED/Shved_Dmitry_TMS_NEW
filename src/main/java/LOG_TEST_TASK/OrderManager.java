package LOG_TEST_TASK;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class OrderManager {

    private static final Logger logger = Logger.getLogger(OrderManager.class);
    private static Scanner scanner;

    protected static Order createOrder() {
        scanner = new Scanner(System.in);
        Order order = new Order();

        System.out.println("NAME OF PRODUCT:");
        order.setProductName(scanner.nextLine());
        System.out.println("QUANTITY:");
        while (!scanner.hasNextInt()) {
            logger.warn("USER ENTERED INVALID FORMAT");
            System.err.println("Need a number, try again:");
            scanner.next();
        }
        order.setQuantity(scanner.nextInt());
        System.out.println("TOTAL COST:");
        while (!scanner.hasNextDouble()) {
            logger.warn("USER ENTERED INVALID FORMAT");
            System.err.println("Need a number, try again:");
            scanner.next();
        }
        order.setTotalCost(scanner.nextDouble());
        return order;
    }

    protected static boolean writeOrder(Order order, File file) {
        Writer writer = null;

        try {
            writer = new FileWriter(file, true);
            writer.write("PRODUCT NAME: " + order.getProductName() + "\n" + "QUANTITY: " + order.getQuantity() +
                    "\n" + "TOTAL COST: " + order.getTotalCost() + "\n\n");
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return false;
    }

    protected static int function() {
        scanner = new Scanner(System.in);
        int fun;

        System.out.println("1- WRITE ORDER TO FILE\n0- exit");
        while (!scanner.hasNextInt()) {
            System.out.println("Need a number, try again:");
            scanner.next();
        }
        fun = scanner.nextInt();
        return fun;
    }

    public static void showMenu() {
        File file = new File("src/main/java/LOG_TEST_TASK/ORDERS.txt");

        while (function() != 0) {
            logger.info("USER STARTS WRITING ORDER TO FILE");
            if (writeOrder(createOrder(), file)) {
                logger.info("SUCCESSFUL");
            } else {
                logger.info("FAILURE");
            }
        }
        logger.info("USER DISCONNECTED");
    }
}
