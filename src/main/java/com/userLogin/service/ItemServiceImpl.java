package com.userLogin.service;

import com.userLogin.model.Item;
import com.userLogin.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Long createItem(Item item) throws Exception {
        return itemRepository.createItem(item);
    }

    @Override
    public void updateItem(Item item) {
        itemRepository.updateItem(item);
    }

    @Override
    public void deleteItemById(Long id) throws Exception {
        itemRepository.deleteItemById(id);
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.getItemById(id);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    @Override
    public void updateItemStock(Long id, Long newStock) {
        itemRepository.updateItemStock(id, newStock);
    }

    @Override
    public List<Item> SearchItemsByLetter(String letter) {
        return itemRepository.SearchItemsByLetter(letter);
    }


}
