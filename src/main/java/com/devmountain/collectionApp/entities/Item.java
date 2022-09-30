package com.devmountain.collectionApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.devmountain.collectionApp.dtos.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = true)
    private String brand;

    @Column
    private String stock_photo;

    @Column
    private Float original_price;

    @Column
    private String user_photo;

    @Column
    private Float amount_paid;

    @Column
    private Date date_acquired;

    @Column
    private String current_location;

    @Column
    private Float current_value;

    @Column
    private String keywords;

    @Column(columnDefinition = "text")
    private String notes;

    @ManyToOne
    @JsonBackReference
    private User user;

    @ManyToOne
    @JsonBackReference
    private Collection collection;



    public Item(ItemDto itemDto){
        if(itemDto.getName() != null){
            this.name = itemDto.getName();
        }
        if(itemDto.getBrand() != null){
            this.brand = itemDto.getBrand();
        }
        if (itemDto.getStock_photo() != null){
            this.stock_photo = itemDto.getStock_photo();
        }
        if (itemDto.getOriginal_price() != null){
            this.original_price = itemDto.getOriginal_price();
        }
        if (itemDto.getKeywords() != null){
            this.keywords = itemDto.getKeywords();
        }
        if (itemDto.getUser_photo() != null){
            this.user_photo = itemDto.getUser_photo();
        }
        if (itemDto.getAmount_paid() != null){
            this.amount_paid = itemDto.getAmount_paid();
        }
        if (itemDto.getDate_acquired() != null){
            this.date_acquired = itemDto.getDate_acquired();
        }
        if (itemDto.getCurrent_location() != null){
            this.current_location = itemDto.getCurrent_location();
        }
        if (itemDto.getCurrent_value() != null){
            this.current_value = itemDto.getCurrent_value();
        }
        if (itemDto.getNotes() != null){
            this.notes = itemDto.getNotes();
        }

    }



}
