package com.userLogin.service;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.User;
import com.userLogin.model.UserRequest;

import java.util.List;

public interface UserService {
    Long createUser(UserRequest userRequest) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUserById(Long id) throws Exception;
    User getUserById(Long id) throws Exception;
    User findUserByUsername(String username);
    void addFavoriteItem(Long userId, Long itemId) throws Exception;
    List<Item> getFavoriteItems(Long userId);
    List<Order> getOrders(Long userId);
    void removeItemFromFavorites(Long userId, Long itemId);
    void addItemToUserOrder(Long userId, Long itemId) throws Exception;
    void removeItemFromUserOrder(Long orderId, Long itemId, Long userId) throws Exception;
    List<Item> getOrderItemListByUserId(Long userId);
    List<Order> getClosedOrdersByUserId(Long userId);
    List<Order> getTempOrdersByUserId(Long userId);
}
