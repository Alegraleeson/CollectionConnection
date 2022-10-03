package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.CollectionDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CollectionService {
    //    add a collection
    @Transactional
    CollectionDto addCollection(CollectionDto collectionDto, Long userId);

    //    add a collection
//    @Transactional
//    CollectionDto addCollection(CollectionDto collectionDto, Long userId);

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

//    public Optional<CollectionDto> getImageById(Long collectionId);

//    Optional<CollectionDto> getCollection(Long collectionId);

    List<String> goToCollection(CollectionDto collectionDto);



//    CollectionDto save(CollectionDto collectionDto);

//    @Transactional
//    CollectionDto addCollection(Long userId, MultipartFile file, CollectionDto collectionDto);
}
