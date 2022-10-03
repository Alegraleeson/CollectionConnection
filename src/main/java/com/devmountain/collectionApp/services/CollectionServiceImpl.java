package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.CollectionDto;
import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.User;
import com.devmountain.collectionApp.repositories.CollectionRepository;
import com.devmountain.collectionApp.repositories.ItemRepository;
import com.devmountain.collectionApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ItemRepository itemRepository;

    //    add a collection
    @Override
    @Transactional
    public CollectionDto addCollection(CollectionDto collectionDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Collection collection = new Collection(collectionDto);
        userOptional.ifPresent(collection::setUser);
        collectionRepository.saveAndFlush(collection);

        return collectionDto;
    }

    //    delete a collection
    @Override
    @Transactional
    public void deleteCollectionById(Long collectionId) {
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        collectionOptional.ifPresent(collection -> collectionRepository.delete(collection));
    }

    //    update collection
    @Override
    @Transactional
    public void updateCollectionById(CollectionDto collectionDto) {
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionDto.getId());
        collectionOptional.ifPresent(collection -> {
            collection.setName(collectionDto.getName());

            collectionRepository.saveAndFlush(collection);
        });
    }

    //    find all collections by user
    @Override
    public List<CollectionDto> getAllCollectionsByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            List<Collection> collectionList = collectionRepository.findAllByUserEquals(userOptional.get());
            return collectionList.stream().map(collection -> new CollectionDto(collection)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

//        get a collection by collection id
    @Override
    public Optional<CollectionDto> getCollectionById(Long collectionId){
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()){
            return Optional.of(new CollectionDto((Collection) collectionOptional.get()));
        }
        return Optional.empty();
    }

//    @Override
//    public Optional<CollectionDto> getImageById(Long collectionId){
//        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
//        if (collectionOptional.isPresent()){
//            return Optional.of(new CollectionDto((Collection) collectionOptional.get()));
//        }
//        return Optional.empty();
//    }

    @Override
    public List<String> goToCollection(CollectionDto collectionDto){
        List<String> response = new ArrayList<>();
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionDto.getId());
        if (collectionOptional.isPresent()){
            response.add("http://localhost:8080/items.html");
            response.add(String.valueOf(collectionOptional.get().getId()));
        } else {
            response.add("Collection cannot be found");
        }
        return response;
    }
//    @Override
//    public CollectionDto save(CollectionDto collectionDto){
//        Optional<Image> imageOptional = null;
//        Collection collection = new Collection(collectionDto);
//        imageOptional.ifPresent(collection::setImage);
//        collectionRepository.saveAndFlush(collection);
//
//        return collectionDto;
//    }


//    @Override
//    @Transactional
//    public CollectionDto addCollection(Long userId, MultipartFile file, CollectionDto collectionDto) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        Collection collection = new Collection(collectionDto);
//
//        byte[] bArray = null;
//
//
//        try {
//            bArray = new byte[file.getBytes().length];
//            int i = 0;
//            for(byte b : file.getBytes()){
//                bArray[i++] = b;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        CollectionDto collectionDto = new CollectionDto(name, bArray);
//        collectionDto.setName(name);

//        userOptional.ifPresent(collection::setUser);
//        collection.setImage(bArray);
//        userOptional.ifPresent(collection::setImage);
//        collectionRepository.saveAndFlush(collection);

//        return "redirect:/adminPage";
//        return collectionDto;
//    }


}

