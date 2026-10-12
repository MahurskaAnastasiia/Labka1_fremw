package org.example.service;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.Product;

import java.util.*;
import java.util.stream.Collectors;
public class OrderServiceImpl implements OrderService {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    @Override
    public void removeOrder(long orderId) {
        orders.removeIf(order -> order.getId() == orderId);
    }

    @Override
    public Order findOrderById(long orderId) {
        return orders.stream()
                .filter(order -> order.getId() == orderId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Order> findOrdersByCustomer(long customerId) {
        return orders.stream()
                .filter(order -> order.getCustomer() != null && order.getCustomer().getId() == customerId)
                .toList();
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus status) {
        return orders.stream()
                .filter(order -> order.getStatus() == status)
                .toList();
    }

    @Override
    public List<Order> findOrdersContainingProduct(long productId) {
        return orders.stream()
                .filter(order -> order.getProducts() != null &&
                        order.getProducts().stream().anyMatch(product -> product.getId() == productId))
                .toList();
    }

    @Override
    public double calculateOrderTotal(long orderId) {
        var order = findOrderById(orderId);
        if (order == null || order.getProducts() == null) {
            return 0.0;
        }
        return order.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public Set<Customer> findCustomersWithOrders() {
        return orders.stream()
                .map(Order::getCustomer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Order> sortOrdersByTotalPrice() {
        return orders.stream()
                .sorted(Comparator.comparingDouble(order -> calculateOrderTotal(order.getId())))
                .toList();
    }

    @Override
    public Map<OrderStatus, Long> countOrdersByStatus() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
    }
}
