package com.userLogin.repository;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.User;
import com.userLogin.repository.mapper.ItemMapper;
import com.userLogin.repository.mapper.OrderMapper;
import com.userLogin.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class UserRepositoryImpl implements UserRepository {




    private static final String USER_TABLE_NAME = "user";

    private final String ORDERS_TABLE_NAME = "order_table";
    private final String USER_ORDER = "USER_ORDER";

    private final String user_favorite_item = "user_favorite_item";
    private final String item = "item";
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public Long createUser(User user) {
        String sql = "INSERT INTO " + USER_TABLE_NAME + " (first_name, last_name, email, phone, address, username, password) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getUsername(),
                user.getPassword()
        );
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM " + USER_TABLE_NAME, Long.class);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE " + USER_TABLE_NAME + " SET first_name=?, last_name=?, email=?, phone=?, address=?, username=?, password=? WHERE id=?";
        jdbcTemplate.update(
                sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getUsername(),
                user.getPassword(),
                user.getId()
        );
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

            String deleteUserOrderSql = "DELETE FROM " + USER_ORDER + " WHERE user_id=?";
            jdbcTemplate.update(deleteUserOrderSql, id);

            String deleteFavoriteListSql = "DELETE FROM " + user_favorite_item + " WHERE user_id=?";
            jdbcTemplate.update(deleteFavoriteListSql, id);

            String deleteOrderSql = "DELETE FROM " + ORDERS_TABLE_NAME + " WHERE user_id=?";
            jdbcTemplate.update(deleteOrderSql, id);

            String deleteUserSql = "DELETE FROM " + USER_TABLE_NAME + " WHERE id=?";
            jdbcTemplate.update(deleteUserSql, id);
        } finally {
            jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
        }
    }


    @Override
    public User getUserById(Long id) {


        String sql = "SELECT * FROM " + USER_TABLE_NAME + " WHERE id=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
            if (user != null) {
                List<Item> favoriteItems = getFavoriteItems(id);
                user.setFavoriteItems(favoriteItems);
                List<Order> tempOrders = getOrders(id);
                user.setUserOrders(tempOrders);

            }
            return user;
        } catch (EmptyResultDataAccessException error) {
            return null;
        }
    }

    @Override
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM " + USER_TABLE_NAME + " WHERE username=?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserMapper(), username);
        } catch (EmptyResultDataAccessException error) {
            return null;
        }
    }

    @Override
    public void addFavoriteItem(Long userId, Long itemId) {
        try {
            String sqlInsert = "INSERT INTO user_favorite_item (user_id, item_id) VALUES (?, ?)";
            jdbcTemplate.update(sqlInsert, userId, itemId);
        } catch (DataAccessException e) {
            logger.error("Failed to add favorite item for user {}, item {}: {}", userId, itemId, e.getMessage());

            throw new RuntimeException("Failed to add favorite item.", e);
        }
    }

    @Override
    public List<Item> getFavoriteItems(Long userId) {
        try {
            String sql = "SELECT i.id, i.title, i.photo_url, i.price_usd, i.available_stock " +
                    "FROM item i " +
                    "JOIN user_favorite_item uf ON i.id = uf.item_id " +
                    "WHERE uf.user_id = ?";
            return jdbcTemplate.query(sql, new ItemMapper(), userId);
        } catch (DataAccessException e) {
            // Log the exception
            logger.error("Failed to retrieve favorite items for user {}: {}", userId, e.getMessage());

            throw new RuntimeException("Failed to retrieve favorite items.", e);
        }
    }

    @Override
    public List<Order> getOrders(Long userId) {
        try {
            String sql = "SELECT * FROM " + ORDERS_TABLE_NAME + " WHERE user_id=?";
            List<Order> orders = jdbcTemplate.query(sql, new OrderMapper(), userId);

            for (Order order : orders) {
                Long orderId = order.getOrderId();
                List<Item> items = getOrderItemListByOrderId(orderId);
                order.setItems(items);
            }

            return orders;
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve orders for user {}: {}", userId, e.getMessage());
            throw new RuntimeException("Failed to retrieve orders.", e);
        }
    }




    @Override
    public void removeItemFromFavorites(Long userId, Long itemId) {
        try {
            String sqlDelete = "DELETE FROM user_favorite_item WHERE user_id = ? AND item_id = ?";

            jdbcTemplate.update(sqlDelete, userId, itemId);
        } catch (DataAccessException e) {
            logger.error("Failed to remove item {} from favorites for user {}: {}", itemId, userId, e.getMessage());

            throw new RuntimeException("Failed to remove item from favorites.", e);
        }
    }



    @Override
    public void addItemToUserOrder(Long orderId, Long itemId, Long userId) {
        String sql = "INSERT INTO " + USER_ORDER + " (order_id, item_id, user_id) VALUES (?,?,?)";


        try {
            jdbcTemplate.update(sql,orderId, itemId, userId);
        } catch (DataAccessException e) {
            logger.error("Failed to add item {} to user order {} for user {}: {}",orderId, itemId, userId, e.getMessage());

            throw new RuntimeException("Failed to add item to user order.", e);
        }
    }




    @Override
    public void removeItemFromUserOrder(Long orderId, Long itemId, Long userId) {
        String sql = "DELETE FROM " + USER_ORDER + " WHERE order_id=? AND item_id=? AND user_id=?";
        jdbcTemplate.update(sql, orderId, itemId, userId);

    }

    @Override
    public List<Item> getOrderItemListByOrderId(Long orderId) {
        String sql = "SELECT item.* FROM USER_ORDER userOrder " +
                "JOIN item ON userOrder.item_id = item.id " +
                "WHERE userOrder.order_id=?";
        try {
            return jdbcTemplate.query(sql, new ItemMapper(), orderId);
        } catch (EmptyResultDataAccessException error) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Order> getClosedOrdersByUserId(Long userId) {


        String sql = "SELECT * FROM " + ORDERS_TABLE_NAME + " WHERE user_id=? AND status='CLOSE'";
        try {
            List<Order> orders = jdbcTemplate.query(sql, new OrderMapper(), userId);
            for (Order order : orders) {
                List<Item> itemList = getOrderItemListByOrderId(order.getOrderId());
                order.setItems(itemList);
            }
            return orders;
        } catch (EmptyResultDataAccessException error) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Order> getTempOrdersByUserId(Long userId) {
        String sql = "SELECT * FROM " + ORDERS_TABLE_NAME + " WHERE user_id=? AND status='TEMP'";
        try {
            List<Order> orders = jdbcTemplate.query(sql, new OrderMapper(), userId);
            for (Order order : orders) {
                List<Item> itemList = getOrderItemListByOrderId(order.getOrderId());
                order.setItems(itemList);
            }
            return orders;
        } catch (EmptyResultDataAccessException error) {
            return Collections.emptyList();
        }
    }
}




