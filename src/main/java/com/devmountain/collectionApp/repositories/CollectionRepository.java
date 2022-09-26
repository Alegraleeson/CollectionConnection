package com.devmountain.collectionApp.repositories;
import com.devmountain.collectionApp.entities.Item;
import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findAllByUserEquals(User user);

//    List<Item> findCollectionsByItem(Long itemId);
}
