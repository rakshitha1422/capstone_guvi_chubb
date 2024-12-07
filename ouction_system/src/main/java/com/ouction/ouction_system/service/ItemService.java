package com.ouction.ouction_system.service;

import com.ouction.ouction_system.Repositery.ItemRepository;
import com.ouction.ouction_system.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    // Save Item
    public Item saveItem(Item item) {
        Item item1=new Item();
        item1.setAuctionId(item.getAuctionId());
        item1.setName(item.getName());
        item1.setDescription(item.getDescription());
        item1.setImage(item.getImage());
        item1.setCategory(item.getCategory());
        item1.setTags(item.getTags());
        item1.setAddedDate(LocalDate.now().atStartOfDay());


        return itemRepository.save(item1);
    }

    // Get all Items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Get Item by Id
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    // Delete Item by Id
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
