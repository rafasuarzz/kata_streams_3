package example.model;

public class Customer {
    private final String id;
    private final String name;
    private final Integer tier;

    public Customer(String id, String name, Integer tier) {
        this.id = id;
        this.name = name;
        this.tier = tier;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTier() {
        return tier;
    }
}