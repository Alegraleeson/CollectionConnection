package com.devmountain.collectionApp.entities;

import com.devmountain.collectionApp.dtos.WishlistDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.devmountain.collectionApp.dtos.CollectionDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Wishlists")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String name;

    @Column
    private String image;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "wishlist", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Item> itemSet = new HashSet<>();



    public Wishlist(WishlistDto wishlistDto){
        if (wishlistDto.getName() != null){
            this.name = wishlistDto.getName();
        }
        if (wishlistDto.getImage() != null){
            this.image = wishlistDto.getImage();
        }

    }




}
