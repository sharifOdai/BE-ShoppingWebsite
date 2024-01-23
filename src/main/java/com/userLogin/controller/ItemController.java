package com.userLogin.controller;

import com.userLogin.model.Item;
import com.userLogin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        try {
            itemService.createItem(item);
            return ResponseEntity.ok("Item created successfully.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable Long itemId) {
        Item item = itemService.getItemById(itemId);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable Long itemId, @RequestBody Item updatedItem) {
        try {
            updatedItem.setId(itemId);
            itemService.updateItem(updatedItem);
            return ResponseEntity.ok("Item updated successfully.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        try {
            itemService.deleteItemById(itemId);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/getAllItems")
    @CrossOrigin
    public ResponseEntity<List<Item>> getAllItems (){
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/itemSearch")
    @CrossOrigin
    public  ResponseEntity<List<Item>> searchResult (@RequestParam String letter){
        List<Item> itemList = itemService.SearchItemsByLetter(letter);
        return ResponseEntity.ok(itemList);
    }
}
