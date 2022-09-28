package com.devmountain.collectionApp.controllers;

import com.devmountain.collectionApp.dtos.CollectionDto;
import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.Item;
import com.devmountain.collectionApp.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devmountain.collectionApp.services.ItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/collection/{collectionId}")
    public List<ItemDto> getItemsByCollection(@PathVariable Long collectionId){
        return itemService.getAllItemsByCollectionId(collectionId);
    }

    //    add a new item
    @PostMapping("/user/{userId}")
    public void addItem(@RequestBody ItemDto itemDto,@PathVariable Long userId){
        itemService.addItem(itemDto, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteNoteById(@PathVariable Long itemId){
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
