import example.model.Customer;
import example.model.Order;
import example.model.Product;
import example.repositories.CustomerRepository;
import example.repositories.OrderRepository;
import example.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsTest {
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        productRepository = new ProductRepository().addAll(testProducts());
        customerRepository = new CustomerRepository().addAll(testCustomers());
        orderRepository = new OrderRepository().addAll(testOrders());
    }
    @Test
    public void should_obtain_products_belongs_to_category_Books_with_price_greater_than_100() throws IOException {
        List<Product> books = productRepository.stream()
                .filter(product -> product.getCategory().equals("Books"))
                .filter(product -> product.getPrice() > 100)
                .collect(Collectors.toList());
        System.out.println(books);
    }

    @Test
    public void should_obtain_a_list_of_order_with_products_belong_to_category_Baby() {
        List<Order> orders = orderRepository.stream()
                .filter(o -> hasBabyProducts(o))
                .collect(Collectors.toList());
    }

    private boolean hasBabyProducts(Order order) {
        return order.getProducts().stream().anyMatch(p -> p.getCategory().equals("Baby"));
        //return order.getProducts().stream()
        //		.filter(p -> p.getCategory().equals("Baby"))
        //		.findFirst()
        //		.isPresent();
    }

    @Test
    public void should_obtain_a_list_of_product_with_category_Toys_and_then_apply_10perc_discount() {
        List<Product> toys = productRepository.stream()
                .filter(product -> product.getCategory().equals("Toys"))
                .map(product -> product.withPrice(product.getPrice() * 0.9))
                .collect(Collectors.toList());
    }

    @Test
    public void should_obtain_a_list_of_products_ordered_by_customers_of_tier_2_between_01Feb2021_and_01Apr2021() {
        List<Product> collect = orderRepository.stream().parallel()
                .filter(o -> o.getCustomer().getTier().equals(2))
                .filter(o -> o.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)))
                .filter(o -> o.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .map(o -> o.getProducts())
                .flatMap(lp -> lp.stream())
                .collect(Collectors.toList());
    }

    @Test
    public void should_get_the_cheapest_product_of_Babys_category() {
        Product baby = productRepository.stream()
                .filter(p -> p.getCategory().equals("Babys"))
                .min(Comparator.comparing(product -> product.getPrice())).orElse(null);


        System.out.println();
    }

    @Test
    public void should_get_the_cheapest_products_of_Babys_category() {
        List<Product> baby = productRepository.stream()
                .filter(p -> p.getCategory().equals("Babys"))
                .reduce(new ArrayList<>(), (l, p) -> {
                    if (l.isEmpty()|| l.get(0).getPrice()== p.getPrice()) l.add(p);
                    else if (p.getPrice() < l.get(0).getPrice()) {
                        l.clear();
                        l.add(p);
                    }
                    return l;
                }, (l1, l2) -> l1);
    }

    @Test
    public void should_get_a_list_of_orders_which_were_ordered_on_1Mar2021_log_the_order_records_to_the_console_and_then_return_its_product_list() {
        List<Product> products = orderRepository.stream()
                .filter(o -> o.getOrderDate().equals(LocalDate.of(2021, 3, 1)))
                .peek(o -> System.out.println(o.toString()))
                .flatMap(o -> o.getProducts().stream())
                .collect(Collectors.toList());
    }

    @Test
    public void should_calculate_total_lump_sum_of_all_orders_placed_in_Feb_2021() {
        Double totalCost = orderRepository.stream()
                .filter(o -> o.getOrderDate().getMonthValue() == 2 && o.getOrderDate().getYear() == 2021)
                .map(o -> orderCost(o))
                .reduce(0d, (a, b) -> Double.sum(a, b)); //(c1, c2) -> c1 + c2
    }

    private Double orderCost(Order o) {
        return o.getProducts().stream().mapToDouble(p -> p.getPrice()).sum();
    }

    @Test
    public void should_obtain_a_data_map_with_order_id_and_products_count() {
        Map<String, Integer> map = orderRepository.stream()
                .collect(Collectors.toMap(o -> o.getId(), o -> o.getProducts().size()));
    }

    @Test
    public void should_produce_a_data_map_with_order_records_grouped_by_customer() {
        Map<Customer, List<Order>> group = orderRepository.stream()
                .collect(Collectors.groupingBy(o -> o.getCustomer()));

    }

    private List<Product> testProducts() {
        Random random = new Random();
        return List.of(
                new Product(UUID.randomUUID().toString(), "Game of Thrones I", "Books", random.nextInt(130)),
                new Product(UUID.randomUUID().toString(), "Game of Thrones II", "Books", random.nextInt(100)),
                new Product(UUID.randomUUID().toString(), "The name of the wind", "Books", random.nextInt(100)),
                new Product(UUID.randomUUID().toString(), "cot", "Babys", random.nextInt(100)),
                new Product(UUID.randomUUID().toString(), "diapers", "Babys", random.nextInt(20)),
                new Product(UUID.randomUUID().toString(), "cards game", "Toys", random.nextInt(100)),
                new Product(UUID.randomUUID().toString(), "Monopoly", "Toys", random.nextInt(100)),
                new Product(UUID.randomUUID().toString(), "Trivial", "Toys", random.nextInt(100))
        );
    }

    private List<Customer> testCustomers() {
        Random random = new Random();
        return List.of(
                new Customer(UUID.randomUUID().toString(), "Elvira", random.nextInt(4)),
                new Customer(UUID.randomUUID().toString(), "Laura", random.nextInt(4)),
                new Customer(UUID.randomUUID().toString(), "Maria", random.nextInt(4)),
                new Customer(UUID.randomUUID().toString(), "Antonio", random.nextInt(4)),
                new Customer(UUID.randomUUID().toString(), "Marcos", random.nextInt(4)),
                new Customer(UUID.randomUUID().toString(), "Andres", random.nextInt(4)),
                new Customer(UUID.randomUUID().toString(), "Yaiza", random.nextInt(4))
        );
    }


    private List<Order> testOrders() {
        Random random = new Random();
        return List.of(
                testOrder(random),
                testOrder(random),
                testOrder(random),
                testOrder(random),
                testOrder(random),
                testOrder(random)
        );
    }

    private Order testOrder(Random random) {
        return new Order(UUID.randomUUID().toString(),
                LocalDate.now().minus(random.nextInt(100), ChronoUnit.DAYS),
                LocalDate.now(),
                "Available",
                customerRepository.get(random.nextInt(customerRepository.size())),
                shuffleProducts(random.nextInt(10)));
    }

    private List<Product> shuffleProducts(int size) {
        Random random = new Random();
        return IntStream.range(0, size).mapToObj(i -> productRepository.get(random.nextInt(productRepository.size()))).collect(Collectors.toList());
    }
}