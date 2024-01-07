package example.repositories;

import example.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProductRepository {
    private final List<Product> store;

    public ProductRepository() {
        store = new ArrayList<>();
    }

    public Stream<Product> stream() {
        return store.stream();
    }

    public ProductRepository addAll(List<Product> products) {
        this.store.addAll(products);
        return this;
    }

    public Product get(int index) {
        return store.get(index);
    }

    public int size() {
        return store.size();
    }
}