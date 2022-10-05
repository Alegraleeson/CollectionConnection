package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.WishlistDto;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface WishlistService {
    //    add a Wishlist
    @Transactional
    WishlistDto addWishlist(WishlistDto wishlistDto, Long userId);


    //    delete a wishlist
    @Transactional
    void deleteWishlistById(Long wishlistId);

    //    update wishlist
    @Transactional
    void updateWishlistById(WishlistDto wishlistDto);

    //    find all wishlists by user
    List<WishlistDto> getAllWishlistsByUserId(Long userId);

    //        get a wishlist by wishlist id
    Optional<WishlistDto> getWishlistById(Long wishlistId);


    List<String> goToWishlist(WishlistDto wishlistDto);



}
