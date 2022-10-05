package com.devmountain.collectionApp.controllers;


import com.devmountain.collectionApp.dtos.WishlistDto;
import com.devmountain.collectionApp.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/wishlists")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/user/{userId}")
    public List<WishlistDto> getWishlistsByUser(@PathVariable Long userId) {
        return wishlistService.getAllWishlistsByUserId(userId);
    }

    //        add a new Wishlist
    @PostMapping("/user/{userId}")
    public void addWishlist(@RequestBody WishlistDto wishlistDto, @PathVariable Long userId) {
        wishlistService.addWishlist(wishlistDto, userId);
    }

    @DeleteMapping("/{wishlistId}")
    public void deleteWishlistById(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlistById(wishlistId);
    }

    @PutMapping
    public void updateWishlist(@RequestBody WishlistDto wishlistDto) {
        wishlistService.updateWishlistById(wishlistDto);
    }

    @GetMapping("/{wishlistId}")
    public Optional<WishlistDto> getWishlistById(@PathVariable Long wishlistId) {
        return wishlistService.getWishlistById(wishlistId);
    }

}
