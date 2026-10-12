package org.example;
import org.example.model.*;
import org.example.service.OrderService;
import org.example.service.OrderServiceImpl;

import java.util.List;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderServiceImpl();
        var customer1 = new Customer(1L, "Олексієнко Олексій", "aleksa@gmail.com");
        var customer2 = new Customer(2L, "Роботчук Марія", "mariya@ukr.net");
        var customer3 = new Customer(3L, "Дякуленко Іван", "ivan@gmail.com");

        var laptop = new Product(30L, "Ноутбук ASUS", ProductCategory.ELECTRONICS, 32000.0);
        var phone = new Product(33L, "Iphone 18 Pro", ProductCategory.ELECTRONICS, 61000.0);
        var cosmetic = new Product(38L, "Kiko", ProductCategory.CLOTHING, 1700.0);
        var book = new Product(37L, "Хімія смерті", ProductCategory.BOOKS, 450.0);
        var joy = new Product(39L, "М'яка іграшка'Пушин'XL ", ProductCategory.HOME, 2700.0);

        orderService.addOrder(new Order(1L, customer1, List.of(laptop, book), OrderStatus.NEW));
        orderService.addOrder(new Order(2L, customer2, List.of(phone, cosmetic, joy), OrderStatus.PROCESSING));
        orderService.addOrder(new Order(3L, customer1, List.of(cosmetic, joy), OrderStatus.SHIPPED));
        orderService.addOrder(new Order(4L, customer3, List.of(book), OrderStatus.DELIVERED));
        orderService.addOrder(new Order(5L, customer2, List.of(laptop), OrderStatus.CANCELLED));

        printSection("1. Пошук замовлення за ID");
        var foundOrder = orderService.findOrderById(2L);
        System.out.println("Знайдене замовлення №2: " + foundOrder);

        printSection("2. Пошук замовлень за ID клієнта");
        var customer1Orders = orderService.findOrdersByCustomer(1L);
        System.out.println("Замовлення клієнта ID = 1 (" + customer1.getName() + "):");
        customer1Orders.forEach(order -> System.out.println("  " + order));

        printSection("3. Пошук замовлень за статусом");
        var processingOrders = orderService.findOrdersByStatus(OrderStatus.PROCESSING);
        System.out.println("Замовлення зі статусом PROCESSING:");
        processingOrders.forEach(order -> System.out.println("  " + order));

        printSection("4. Пошук замовлень з конкретним товаром");
        var laptopOrders = orderService.findOrdersContainingProduct(30L);
        System.out.println("Замовлення, які містять 'Ноутбук ASUS' (ID = 30):");
        laptopOrders.forEach(order -> System.out.println("  " + order));

        printSection("5. Обчислення загальної суми замовлення");
        double orderTotal = orderService.calculateOrderTotal(2L);
        System.out.printf("Загальна сума замовлення №2: %.2f грн%n", orderTotal);

        printSection("6.Список унікальних замовників");
        var customersWithOrders = orderService.findCustomersWithOrders();
        System.out.println("Клієнти, які мають хоча б одне замовлення:");
        customersWithOrders.forEach(customer -> System.out.println("  " + customer));

        printSection("7. Сортування замовлень за підсумковою вартістю");
        var sortedOrders = orderService.sortOrdersByTotalPrice();
        System.out.println("Замовлення, відсортовані за зростанням суми:");
        sortedOrders.forEach(order ->
                System.out.printf("  Замовлення №%d | Сума: %.2f грн | Статус: %s%n",
                        order.getId(),
                        orderService.calculateOrderTotal(order.getId()),
                        order.getStatus())
        );

        printSection("8. Підрахунок замовлень за їх статусом ");
        var statusCounts = orderService.countOrdersByStatus();
        System.out.println("Кількість замовлень у кожному статусі:");
        statusCounts.forEach((status, count) ->
                System.out.printf("  %-12s : %d%n", status, count)
        );

        printSection("9. Видалення замовлення");
        orderService.removeOrder(5L);
        System.out.println("Результат пошуку замовлення №5 після видалення: " + orderService.findOrderById(5L));
        System.out.println("Оновлений список за статусами:");
        orderService.countOrdersByStatus().forEach((status, count) ->
                System.out.printf("  %-12s : %d%n", status, count)
        );
    }

    private static void printSection(String title) {
        System.out.println(">>> " + title);
    }
}