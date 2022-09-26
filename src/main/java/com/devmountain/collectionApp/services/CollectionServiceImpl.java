package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.CollectionDto;
import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.Item;
import com.devmountain.collectionApp.entities.User;
import com.devmountain.collectionApp.repositories.CollectionRepository;
import com.devmountain.collectionApp.repositories.ItemRepository;
import com.devmountain.collectionApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void addCollection(CollectionDto collectionDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Collection collection = new Collection(collectionDto);
        userOptional.ifPresent(collection::setUser);
        collectionRepository.saveAndFlush(collection);

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

    //    get a collection by collection id
    @Override
    public Optional<CollectionDto> getCollectionById(Long collectionId){
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()){
            return Optional.of(new CollectionDto((Collection) collectionOptional.get()));
        }
        return Optional.empty();
    }

}
