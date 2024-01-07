package example.repositories;

import example.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OrderRepository {
    private final List<Order> store;

    public OrderRepository() {
        this.store = new ArrayList<>();
    }

    public Stream<Order> stream() {
        return store.stream();
    }

    public OrderRepository addAll(List<Order> orders) {
        this.store.addAll(orders);
        return this;
    }

    public int size() {
        return store.size();
    }
}
