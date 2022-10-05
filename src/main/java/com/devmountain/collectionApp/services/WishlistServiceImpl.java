package com.devmountain.collectionApp.services;

import com.devmountain.collectionApp.dtos.WishlistDto;
import com.devmountain.collectionApp.entities.Wishlist;
import com.devmountain.collectionApp.entities.User;
import com.devmountain.collectionApp.repositories.WishlistRepository;
import com.devmountain.collectionApp.repositories.ItemRepository;
import com.devmountain.collectionApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ItemRepository itemRepository;

    //    add a wishlist
    @Override
    @Transactional
    public WishlistDto addWishlist(WishlistDto wishlistDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Wishlist wishlist = new Wishlist(wishlistDto);
        userOptional.ifPresent(wishlist::setUser);
        wishlistRepository.saveAndFlush(wishlist);

        return wishlistDto;
    }

    //    delete a wishlist
    @Override
    @Transactional
    public void deleteWishlistById(Long wishlistId) {
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);
        wishlistOptional.ifPresent(wishlist -> wishlistRepository.delete(wishlist));
    }

    //    update wishlist
    @Override
    @Transactional
    public void updateWishlistById(WishlistDto wishlistDto) {
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistDto.getId());
        wishlistOptional.ifPresent(wishlist -> {
            wishlist.setName(wishlistDto.getName());

            wishlistRepository.saveAndFlush(wishlist);
        });
    }

    //    find all wishlists by user
    @Override
    public List<WishlistDto> getAllWishlistsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Wishlist> wishlistList = wishlistRepository.findAllByUserEquals(userOptional.get());
            return wishlistList.stream().map(wishlist -> new WishlistDto(wishlist)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    //        get a wishlist by wishlist id
    @Override
    public Optional<WishlistDto> getWishlistById(Long wishlistId) {
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistId);
        if (wishlistOptional.isPresent()) {
            return Optional.of(new WishlistDto((Wishlist) wishlistOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<String> goToWishlist(WishlistDto wishlistDto) {
        List<String> response = new ArrayList<>();
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(wishlistDto.getId());
        if (wishlistOptional.isPresent()) {
            response.add("http://localhost:8080/wishItems.html");
            response.add(String.valueOf(wishlistOptional.get().getId()));
        } else {
            response.add("Wishlist cannot be found");
        }
        return response;
    }
}