package com.devmountain.collectionApp.controllers;

import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.devmountain.collectionApp.services.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/from/{collectionId}")
    public List<ItemDto> getItemsByCollection(@PathVariable Long collectionId){
        List<ItemDto> collectionOptional = itemService.getAllItemsByCollectionId(collectionId);
        List<ItemDto> none = new ArrayList<>();
        if(!collectionOptional.isEmpty()){
            return collectionOptional;
        }
            return none;
    }

    @GetMapping("/wishlists/{wishlistId}")
    public List<ItemDto> getItemsByWishlistId(@PathVariable Long wishlistId){
        List<ItemDto> wishlistOptional = itemService.getAllItemsByWishlistId(wishlistId);
        List<ItemDto> none = new ArrayList<>();
        if(!wishlistOptional.isEmpty()){
            return wishlistOptional;
        }
        return none;
    }

    //    add a new item
    @PostMapping("/collections/{collectionId}/{userId}")
    public void addItem(@RequestBody ItemDto itemDto,@PathVariable Long collectionId, @PathVariable Long userId){
        itemService.addItem(itemDto, userId, collectionId);
    }

    @PostMapping("/wishlists/{wishlistId}/{userId}")
    public void addWishItem(@RequestBody ItemDto itemDto,@PathVariable Long wishlistId, @PathVariable Long userId){
        itemService.addWishItem(itemDto, userId, wishlistId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable Long itemId){
        itemService.deleteItemById(itemId);
    }

    @PutMapping
    public void updateItem(@RequestBody ItemDto itemDto){
        itemService.updateItemById(itemDto);
    }

    @GetMapping("/{itemId}")
    public Optional<ItemDto> getItemById(@PathVariable Long itemId){
        return itemService.getItemById(itemId);
    }

}
