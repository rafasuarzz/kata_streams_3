package example.repositories;

import example.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CustomerRepository {
    private final List<Customer> store;

    public CustomerRepository() {
        this.store = new ArrayList<>();
    }

    public Stream<Customer> stream() {
        return store.stream();
    }

    public CustomerRepository addAll(List<Customer> orders) {
        this.store.addAll(orders);
        return this;
    }

    public Customer get(int index) {
        return store.get(index);
    }

    public int size() {
        return store.size();
    }


}
