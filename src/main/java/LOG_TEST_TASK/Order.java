package LOG_TEST_TASK;

import java.util.Objects;

public class Order {

    private String productName;
    private int quantity;
    private double totalCost;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && Double.compare(order.totalCost, totalCost) == 0 && productName.equals(order.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, quantity, totalCost);
    }
}
