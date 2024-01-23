package com.userLogin.repository;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.User;


import java.util.List;
public interface OrderRepository {

    Long createOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Long orderId);
    Order getOrderById(Long orderId);
    List<Order> getAllOrders();
    List<Item> getItemsByOrderId(Long orderId);
    List<Order> getOrdersByUserId(Long userId);
    Order getOrderByUserId(Long userId);
    Order getTempOrderByUserId(Long userId);
    void removeFromTempOrders(Long orderId);
}
