package com.userLogin.repository;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.User;

import java.util.List;

public interface UserRepository {
    Long createUser(User user);
    void updateUser(User user);
    void deleteUserById(Long id);
    User getUserById(Long id);
    User findUserByUsername(String username);
    void addFavoriteItem(Long userId, Long itemId);
    List<Item> getFavoriteItems(Long userId);
    List<Order> getOrders(Long userId);
    void removeItemFromFavorites(Long userId, Long itemId);
    void addItemToUserOrder(Long orderId, Long itemId, Long userId);
    void removeItemFromUserOrder(Long orderId, Long itemId, Long userId);
    List<Item> getOrderItemListByOrderId(Long orderId);
    List<Order> getClosedOrdersByUserId(Long userId);
    List<Order> getTempOrdersByUserId(Long userId);
}
