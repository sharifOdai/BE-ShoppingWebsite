package com.userLogin.service;
import com.userLogin.model.Item;
import java.util.List;

public interface ItemService {

    Long createItem(Item item) throws Exception;
    void updateItem(Item item) ;
    void deleteItemById(Long id) throws Exception;
    Item getItemById(Long id);
    List<Item> getAllItems();
    void updateItemStock(Long id, Long newStock);
    List<Item> SearchItemsByLetter(String letter);



}
