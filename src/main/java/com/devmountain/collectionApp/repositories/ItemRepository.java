package com.devmountain.collectionApp.repositories;

import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.Item;
import com.devmountain.collectionApp.entities.User;
import com.devmountain.collectionApp.services.CollectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository  extends JpaRepository<Item, Long> {


    @Query(value = "SELECT collections FROM collection_items JOIN items ON items.item_id = collection_items.items", nativeQuery = true)
    List<Item> findAllByCollectionsEquals(Long collectionId);


}

