package com.devmountain.collectionApp.dtos;

import com.devmountain.collectionApp.entities.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {
    private Long id;
    private String name;

    private String image;

    private UserDto userDto;
    private ItemDto itemDto;


    public WishlistDto(Wishlist wishlist) {
        if (wishlist.getId() != null) {
            this.id = wishlist.getId();
        }
        if (wishlist.getName() != null) {
            this.name = wishlist.getName();
        }
        if (wishlist.getImage() != null) {
            this.image = wishlist.getImage();
        }


    }
}