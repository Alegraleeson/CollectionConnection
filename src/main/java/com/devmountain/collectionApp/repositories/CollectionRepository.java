package com.devmountain.collectionApp.repositories;
import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findAllByUserEquals(User user);

    Optional<Collection> findById(Long collectionId);


}
