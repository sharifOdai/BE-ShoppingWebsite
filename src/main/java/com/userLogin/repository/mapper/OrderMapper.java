package com.userLogin.repository.mapper;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.OrderStatus;
import com.userLogin.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderMapper implements RowMapper<Order> {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getLong("order_id"));
        order.setUserId(rs.getLong("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setTotalPrice(rs.getDouble("total_price"));

        String statusValue = rs.getString("status");
        if (statusValue != null) {
            order.setStatus(OrderStatus.valueOf(statusValue));
        } else {
            order.setStatus(OrderStatus.TEMP);
        }


        return order;
    }


}
