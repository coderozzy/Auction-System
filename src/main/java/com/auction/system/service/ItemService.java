package com.auction.system.service;

import com.auction.system.entity.Item;
import com.auction.system.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public void deleteItem(Long id) {
        try {
            Item item = getItemById(id);
            item.setAuction(null);
            item = itemRepository.save(item);
            itemRepository.delete(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
