package org.example.service;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;
public interface OrderService {
    void addOrder(Order order);
    void removeOrder(long orderId);
    Order findOrderById(long orderId);
    List<Order> findOrdersByCustomer(long customerId);
    List<Order> findOrdersByStatus(OrderStatus status);
    List<Order> findOrdersContainingProduct(long productId);
    double calculateOrderTotal(long orderId);
    Set<Customer> findCustomersWithOrders();
    List<Order> sortOrdersByTotalPrice();
    Map<OrderStatus, Long> countOrdersByStatus();

}
