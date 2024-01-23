package com.userLogin.service;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.OrderStatus;
import com.userLogin.model.User;
import com.userLogin.repository.ItemRepository;
import com.userLogin.repository.OrderRepository;
import com.userLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Long createOrder(Order order) {
        User user = userRepository.getUserById(order.getUserId());

        if (user == null){
            return null;
        }

        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }
        order.setShippingAddress(user.getAddress());
        order.setOrderDate(new Date());

        order.setTotalPrice(0.0);

        order.setStatus(OrderStatus.TEMP);

        updateOrder(order);

        return orderRepository.createOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.updateOrder(order);

    }



    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteOrder(orderId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.getOrderById(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public List<Item> getItemsByOrderId(Long orderId) {
        return orderRepository.getItemsByOrderId(orderId);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.getOrdersByUserId(userId);
    }

    @Override
    public Order getOrderByUserId(Long userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    @Override
    public Order getTempOrderByUserId(Long userId) {
        return orderRepository.getTempOrderByUserId(userId);
    }

    @Override
    public void processPayment(Long userId, Long orderId) throws Exception {

        Order order = orderRepository.getOrderById(orderId);

        System.out.println("Processing payment for orderId: " + orderId);
        System.out.println("Order details: " + order);

        if ( order == null || order.getItems().isEmpty()) {
            throw new Exception("No items in the user's order to submit.");

        } else {
            handleStock(order);
            order.setStatus(OrderStatus.CLOSE);
            orderRepository.updateOrder(order);
        }
    }

    @Override
    public void handleStock(Order order) throws Exception{
        for (Item orderItem : order.getItems()) {
            Item selectedItem = itemRepository.getItemById(orderItem.getId());
            if (selectedItem == null) {
                throw new Exception("Item not found: " + orderItem.getId());
            }

            Long itemStock = selectedItem.getAvailableStock();

            if (itemStock <= 0) {
                throw new Exception("Item out of stock: " + orderItem.getId());
            }
            itemRepository.updateItemStock(orderItem.getId(), itemStock - 1);
        }
    }
}
