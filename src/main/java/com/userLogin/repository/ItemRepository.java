package com.userLogin.repository;

import com.userLogin.model.Item;

import java.util.List;

public interface ItemRepository {

    Long createItem(Item item);
    void updateItem(Item item);
    void deleteItemById(Long id);
    Item getItemById(Long id);
    List<Item> getAllItems();
    void updateItemStock(Long id, Long newStock);

    List<Item> SearchItemsByLetter(String letter);
}
