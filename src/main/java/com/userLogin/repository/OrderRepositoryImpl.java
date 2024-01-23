package com.userLogin.repository;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.User;
import com.userLogin.repository.mapper.ItemMapper;
import com.userLogin.repository.mapper.OrderMapper;
import com.userLogin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository

public class OrderRepositoryImpl implements OrderRepository {
    private final String ORDERS_TABLE_NAME = "order_table";
    private final String USER_ORDER = "USER_ORDER";

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long createOrder(Order order) {

            String sql = "INSERT INTO " + ORDERS_TABLE_NAME + " (user_id, order_date, shipping_address, total_price, status) VALUES (?,?,?,?,?)";
            jdbcTemplate.update(
                    sql,
                    order.getUserId(),
                    order.getOrderDate(),
                    order.getShippingAddress(),
                    order.getTotalPrice(),
                    order.getStatus().name()
            );

        return jdbcTemplate.queryForObject("SELECT MAX(order_id) FROM " + ORDERS_TABLE_NAME, Long.class);


    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE " + ORDERS_TABLE_NAME + " SET user_id=?, order_date=?, shipping_address=?, total_price=?, status=? WHERE order_id=?";
        jdbcTemplate.update(
                sql,
                order.getUserId(),
                order.getOrderDate(),
                order.getShippingAddress(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getOrderId()
        );
    }

    @Override
    public void deleteOrder(Long orderId) {
        String sql = "DELETE FROM " + ORDERS_TABLE_NAME + " WHERE order_id=?";
        jdbcTemplate.update(sql, orderId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        String sql = "SELECT * FROM " + ORDERS_TABLE_NAME + " WHERE order_id=?";

        Order order = jdbcTemplate.queryForObject(sql, new OrderMapper(), orderId);

        try {
            if (order != null) {
                List<Item> itemList = userRepository.getOrderItemListByOrderId(orderId);
                order.setItems(itemList);

            }
            return order;
        } catch (EmptyResultDataAccessException error) {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM " + ORDERS_TABLE_NAME;
        return jdbcTemplate.query(sql, new OrderMapper());

    }

    @Override
    public List<Item> getItemsByOrderId(Long orderId) {
        String sql = "SELECT i.* FROM item i " +
                "JOIN USER_ORDER uo ON i.id = uo.item_id " +
                "WHERE uo.user_id = ?";
        return jdbcTemplate.query(sql, new ItemMapper(), orderId);    }


    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        String sql = "SELECT * FROM " + ORDERS_TABLE_NAME + " WHERE user_id=?";
        return jdbcTemplate.query(sql, new OrderMapper(), userId);
    }

    @Override
    public Order getOrderByUserId(Long userId) {
        String sql = "SELECT * FROM " + ORDERS_TABLE_NAME + " WHERE user_id=? AND (status='CLOSE' OR status='TEMP') ORDER BY order_id DESC LIMIT 1";

        try {
            Order order = jdbcTemplate.queryForObject(sql, new OrderMapper(), userId);

            if (order != null) {
                List<Item> itemList = getItemsByOrderId(order.getOrderId());
                order.setItems(itemList);
            }

            return order;

        } catch (EmptyResultDataAccessException error) {
            System.out.println("Failed to get order by user ID: " + error.getMessage());
            return null;
        }
    }

    @Override
    public Order getTempOrderByUserId(Long userId) {
        return getOrderByUserId(userId);
    }

    @Override
    public void removeFromTempOrders(Long orderId) {
    }
}
