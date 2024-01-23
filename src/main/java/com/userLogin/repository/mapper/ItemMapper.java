package com.userLogin.repository.mapper;

import com.userLogin.model.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item item = new Item();
        item.setId(rs.getLong("id"));
        item.setTitle(rs.getString("title"));
        item.setPhotoUrl(rs.getString("photo_url"));
        item.setPriceUSD(rs.getDouble("price_usd"));
        item.setAvailableStock(rs.getLong("available_stock"));
        return item;

    }
}
