package com.devmountain.collectionApp.dtos;

import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto implements Serializable {
    private Long id;
    private String name;
    private String brand;
    private String stock_photo;
    private Float original_price;

    private String user_photo;
    private Float amount_paid;
    private Date date_acquired;
    private String current_location;
    private Float current_value;
    private String keywords;
    private String notes;
    private UserDto userDto;
    private CollectionDto collectionDto;

    public ItemDto(Item item){
        if(item.getId() != null){
            this.id = item.getId();
        }
        if(item.getName() != null){
            this.name = item.getName();
        }
        if (item.getBrand() != null){
            this.brand = item.getBrand();
        }
        if (item.getStock_photo() != null){
            this.stock_photo = item.getStock_photo();
        }
        if (item.getOriginal_price() != null){
            this.original_price = item.getOriginal_price();
        }
        if (item.getUser_photo() != null){
            this.user_photo = item.getUser_photo();
        }
        if (item.getAmount_paid() != null){
            this.amount_paid = item.getAmount_paid();
        }
        if (item.getDate_acquired() != null){
            this.date_acquired = item.getDate_acquired();
        }
        if (item.getCurrent_location() != null){
            this.current_location = item.getCurrent_location();
        }
        if (item.getCurrent_value() != null){
            this.current_value = item.getCurrent_value();
        }
        if (item.getKeywords() != null){
            this.keywords = item.getKeywords();
        }
        if (item.getNotes() != null){
            this.notes = item.getNotes();
        }
    }


}
