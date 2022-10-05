package com.devmountain.collectionApp.repositories;
import com.devmountain.collectionApp.entities.Wishlist;
import com.devmountain.collectionApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUserEquals(User user);

    Optional<Wishlist> findById(Long collectionId);


}
