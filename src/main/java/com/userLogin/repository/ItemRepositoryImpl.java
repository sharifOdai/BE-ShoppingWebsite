package com.userLogin.repository;

import com.userLogin.model.Item;
import com.userLogin.repository.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository{

    private static final String ITEM_TABLE_NAME = "item";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long createItem(Item item) {
        String sql = "INSERT INTO " + ITEM_TABLE_NAME + " (title, photo_url, price_usd, available_stock) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,
                item.getTitle(),
                item.getPhotoUrl(),
                item.getPriceUSD(),
                item.getAvailableStock()
        );
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM " + ITEM_TABLE_NAME, Long.class);

    }

    @Override
    public void updateItem(Item item) {
        String sql = "UPDATE " + ITEM_TABLE_NAME + " SET title=?, photo_url=?, price_usd=?, available_stock=? WHERE id=?";
        jdbcTemplate.update(
                sql,
                item.getTitle(),
                item.getPhotoUrl(),
                item.getPriceUSD(),
                item.getAvailableStock(),
                item.getId()
        );
    }

    @Override
    public void deleteItemById(Long id) {
        String sql = "DELETE FROM " + ITEM_TABLE_NAME + " WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Item getItemById(Long id) {
        String sql = "SELECT * FROM " + ITEM_TABLE_NAME + " WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new ItemMapper(), id);
    }

    @Override
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM " + ITEM_TABLE_NAME;
        return jdbcTemplate.query(sql, new ItemMapper());
    }

    @Override
    public void updateItemStock(Long id, Long newStock) {
        String sql = "UPDATE " + ITEM_TABLE_NAME + " SET available_stock = ? WHERE id = ?";
        jdbcTemplate.update(sql, newStock, id);

        }

    @Override
    public List<Item> SearchItemsByLetter(String letter) {
        String sql = "SELECT * FROM " + ITEM_TABLE_NAME + " WHERE LOWER(title) LIKE LOWER(?)";
        String searchItem = letter + "%";

        return jdbcTemplate.query(sql, new ItemMapper(), searchItem);
    }
}
