package com.devmountain.collectionApp.controllers;

import com.devmountain.collectionApp.dtos.CollectionDto;
import com.devmountain.collectionApp.dtos.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.devmountain.collectionApp.services.CollectionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/user/{userId}")
    public List<CollectionDto> getCollectionsByUser(@PathVariable Long userId){
        return collectionService.getAllCollectionsByUserId(userId);
    }

    //    add a new collection
    @PostMapping("/user/{userId}")
    public void addCollection(@RequestBody CollectionDto collectionDto, @PathVariable Long userId){
        collectionService.addCollection(collectionDto, userId);
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollectionById(@PathVariable Long collectionId){
        collectionService.deleteCollectionById(collectionId);
    }

    @PutMapping
    public void updateCollection(@RequestBody CollectionDto collectionDto){
        collectionService.updateCollectionById(collectionDto);
    }

    @GetMapping("/{collectionId}")
    public Optional<CollectionDto> getCollectionById(@PathVariable Long collectionId){
        return collectionService.getCollectionById(collectionId);
    }


}
