package com.userLogin.service;

import com.userLogin.model.*;
import com.userLogin.repository.ItemRepository;
import com.userLogin.repository.OrderRepository;
import com.userLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Long createUser(UserRequest UserRequest) throws Exception {
        User existingUser = userRepository.findUserByUsername(UserRequest.getUsername());
        if (existingUser != null) {
            throw new Exception("Username " + UserRequest.getUsername() + " is already taken");
        }
        return userRepository.createUser(UserRequest.toCustomUser());
    }

    @Override
    public void updateUser(User user) throws Exception {
        Long userId = user.getId();
        User existingUser = userRepository.getUserById(userId);
        if (existingUser != null) {
            userRepository.updateUser(user);
        } else {
            throw new Exception("User with ID " + userId + " does not exist");
        }
    }

    @Override
    public void deleteUserById(Long id) throws Exception {
        User user = userRepository.getUserById(id);
        if (user != null) {
            userRepository.deleteUserById(id);
        } else {
            throw new Exception("User ID: " + id + " Does Not Exist");
        }
    }

    @Override
    public User getUserById(Long id) throws Exception {
        User user = userRepository.getUserById(id);
        if (user != null) {

            return userRepository.getUserById(id);
        } else {
            throw new Exception("User ID: " + id + " Does Not Exist");
        }
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void addFavoriteItem(Long userId, Long itemId) throws Exception {
        userRepository.addFavoriteItem(userId, itemId);
    }

    @Override
    public List<Item> getFavoriteItems(Long userId) {
        return userRepository.getFavoriteItems(userId);
    }

    @Override
    public List<Order> getOrders(Long userId) {
        return userRepository.getOrders(userId);
    }

    @Override
    public void removeItemFromFavorites(Long userId, Long itemId) {
        userRepository.removeItemFromFavorites(userId, itemId);
    }

    @Override
    public void addItemToUserOrder(Long itemId, Long userId) throws Exception {
        Item item = itemService.getItemById(itemId);
        Order userOrder = orderService.getOrderByUserId(userId);

        if (item.getAvailableStock() > 0) {

        if (userOrder == null || userOrder.getStatus() == OrderStatus.CLOSE ) {
            System.out.println("Creating a new temporary order");
            userOrder = createTempOrder(userId);
            Long orderId = orderService.createOrder(userOrder);
            userOrder.setOrderId(orderId);

            orderService.updateOrder(userOrder);
        }
            System.out.println("Adding item to the order");
            userOrder.addItem(item);
            userRepository.addItemToUserOrder(userOrder.getOrderId(), itemId, userId);
            orderService.updateOrder(userOrder);
        } else {
            throw new Exception("Item with ID " + itemId + " is out of stock.");
        }
    }


    private Order createTempOrder(Long userId){
        Order newOrder = new Order();
        newOrder.setUserId(userId);
        return newOrder;
        }

    @Override
    public void removeItemFromUserOrder(Long orderId, Long itemId, Long userId) throws Exception {
        Order userOrder = orderService.getOrderByUserId(userId);

        if (userOrder.getStatus() == OrderStatus.TEMP) {
            Item itemToRemove = itemRepository.getItemById(itemId);
            if (itemToRemove == null) {
                throw new RuntimeException("Item with ID " + itemId + " not found.");
            } else {
                userOrder.getItems().remove(itemToRemove);
                userRepository.removeItemFromUserOrder(userOrder.getOrderId(),itemId, userId);
                orderService.updateOrder(userOrder);
                userOrder = orderService.getOrderByUserId(userId);
                if (userOrder.getItems().isEmpty()) {
                    orderService.deleteOrder(orderId);
                }
            }
        } else {
            throw new Exception("Cant remove items from CLOSED order.");
        }
    }

    @Override
    public List<Item> getOrderItemListByUserId(Long userId) {
        return userRepository.getOrderItemListByOrderId(userId);
    }

    @Override
    public List<Order> getClosedOrdersByUserId(Long userId) {
        return userRepository.getClosedOrdersByUserId(userId);
    }

    @Override
    public List<Order> getTempOrdersByUserId(Long userId) {
        return userRepository.getTempOrdersByUserId(userId);
    }
}





