package LOG_TEST_TASK;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderManagerTest {

    private @InjectMocks OrderManager orderManager;
    private Order order;
    private File file;

    @Before
    public void setOrder() {
        order = new Order();

        order.setProductName("LAYS");
        order.setQuantity(12);
        order.setTotalCost(123.7);
    }

    @Before
    public void createFile() throws IOException {
        file = new File("src/main/java/LOG_TEST_TASK/TEST_ORDERS.txt");

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Test
    public void createOrder() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("LAYS\n12\n123,7".getBytes());

        System.setIn(in);
        assertEquals(OrderManager.createOrder(), order);
        System.setIn(System.in);
        System.in.close();
        in.close();
    }

    @Test
    public void writeOrder() {
        assertTrue(OrderManager.writeOrder(order,file));
    }

    @Test
    public void function() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());

        System.setIn(in);
        assertEquals(OrderManager.function(), 1);
        System.setIn(System.in);
        System.in.close();
        in.close();
    }

    @After
    public void deleteFile() {
        file.delete();
    }
}