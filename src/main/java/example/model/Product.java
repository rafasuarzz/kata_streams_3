package example.model;

public class Product {
    private String id;
    private String name;
    private String category;
    private double price;

    public Product(String id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Product withPrice(double price) {
        return new Product(id, name, category, price);
    }
}
