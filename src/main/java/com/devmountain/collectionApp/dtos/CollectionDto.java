package com.devmountain.collectionApp.dtos;

import com.devmountain.collectionApp.entities.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {
    private Long id;
    private String name;

    private String image;

//    private byte[] image;

//    private String photo;

    private UserDto userDto;
    private ItemDto itemDto;



    public CollectionDto(Collection collection){
        if(collection.getId() != null){
            this.id = collection.getId();
        }
        if (collection.getName() != null){
            this.name = collection.getName();
        }
        if (collection.getImage() != null){
            this.image = collection.getImage();
        }


    }

//    public String getPhoto(){
//        return photo;
//    }
//    public void setPhoto(String fileName) {
//    }
}
