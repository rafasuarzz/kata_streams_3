package example.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String id;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String status;

    private final Customer customer;
    private final List<Product> products;

    public Order(String id, LocalDate orderDate, LocalDate deliveryDate, String status, Customer customer, List<Product> products) {
        this.id = id;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.customer = customer;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }
}

