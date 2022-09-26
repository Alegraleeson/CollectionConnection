package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.Item;
import com.devmountain.collectionApp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devmountain.collectionApp.repositories.CollectionRepository;
import com.devmountain.collectionApp.repositories.ItemRepository;
import com.devmountain.collectionApp.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ItemRepository itemRepository;

    //    adding an item
    @Override
    @Transactional
    public void addItem(ItemDto itemDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Item item = new Item(itemDto);
        userOptional.ifPresent(item::setUser);
        itemRepository.saveAndFlush(item);

    }

    //    delete an item
    @Override
    @Transactional
    public void deleteItemById(Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        itemOptional.ifPresent(item -> itemRepository.delete(item));
    }

    //    update item
    @Override
    @Transactional
    public void updateItemById(ItemDto itemDto) {
        Optional<Item> itemOptional = itemRepository.findById(itemDto.getId());
        itemOptional.ifPresent(item -> {
            item.setName(itemDto.getName());
            item.setBrand(itemDto.getBrand());
            item.setStock_photo(itemDto.getStock_photo());
            item.setOriginal_price(itemDto.getOriginal_price());
            item.setUser_photo(itemDto.getUser_photo());
            item.setAmount_paid(itemDto.getAmount_paid());
            item.setDate_acquired(itemDto.getDate_acquired());
            item.setCurrent_location(itemDto.getCurrent_location());
            item.setCurrent_value(itemDto.getCurrent_value());
            item.setKeywords(itemDto.getKeywords());
            item.setNotes(itemDto.getNotes());
            itemRepository.saveAndFlush(item);
        });
    }

    //    find all items by collection
    @Override
    public List<ItemDto> getAllItemsByCollectionId(Long collectionId){
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()){
            List<Item> itemList = itemRepository.findAllByCollectionsEquals(collectionOptional.get().getId());
            return itemList.stream().map(item -> new ItemDto(item)).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    //    get a item by item id
    @Override
    public Optional<ItemDto> getItemById(Long itemId){
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isPresent()){
            return Optional.of(new ItemDto(itemOptional.get()));
        }
        return Optional.empty();
    }

}
