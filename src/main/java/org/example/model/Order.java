package org.example.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class Order {
    private long id;
    private Customer customer;
    private List<Product> products;
    private OrderStatus status;

    public Order(long id, Customer customer, List<Product> products, OrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.products = products != null ? new ArrayList<>(products) : new ArrayList<>();
        this.status = status;
    }

    @Override
    public String toString() {
        String customerName = (customer != null) ? customer.getName() : "null";
        int count = (products != null) ? products.size() : 0;
        return String.format("Order{id=%d, customer='%s', productsCount=%d, status=%s}",
                id, customerName, count, status);
    }
}
