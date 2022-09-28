package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.CollectionDto;
import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.dtos.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CollectionService {
    //    add a collection
    @Transactional
    void addCollection(CollectionDto collectionDto, Long userId);

    //    delete a collection
    @Transactional
    void deleteCollectionById(Long collectionId);

    //    update collection
    @Transactional
    void updateCollectionById(CollectionDto collectionDto);

    //    find all collections by user
    List<CollectionDto> getAllCollectionsByUserId(Long userId);

//        get a collection by collection id
    Optional<CollectionDto> getCollectionById(Long collectionId);

//    Optional<CollectionDto> getCollection(Long collectionId);

    List<String> goToCollection(CollectionDto collectionDto);
}
