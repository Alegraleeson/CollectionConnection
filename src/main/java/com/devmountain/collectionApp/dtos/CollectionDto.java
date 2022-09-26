package com.devmountain.collectionApp.dtos;

import com.devmountain.collectionApp.entities.Collection;
import com.devmountain.collectionApp.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {
    private Long id;
    private String name;

    private UserDto userDto;
    private ItemDto itemDto;
    private Set<Item> items = new HashSet<>();

    public CollectionDto(Collection collection){
        if(collection.getId() != null){
            this.id = collection.getId();
        }
        if (collection.getName() != null){
            this.name = collection.getName();
        }


    }

//    public void addItem(Item item) {
//        this.items.add(item);
//        item.getCollections().add(this);
//    }
//
//    public void removeItem(long itemId) {
//        Item item = this.items.stream().filter(item -> item.getId() == itemId).findFirst().orElse(null);
//        if (item != null) {
//            this.items.remove(item);
//            item.getItems().remove(this);
//        }
//    }



}
