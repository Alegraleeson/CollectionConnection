package com.devmountain.collectionApp.entities;

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
@Table(name = "Collections")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String name;

//    (name = "image", unique = false, nullable = false, length = 100000)
    @Column
    private String image;

//    @Column
//    private String photo;


    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Item> itemSet = new HashSet<>();



    public Collection(CollectionDto collectionDto){
        if (collectionDto.getName() != null){
            this.name = collectionDto.getName();
        }
        if (collectionDto.getImage() != null){
            this.image = collectionDto.getImage();
        }

    }

//    public void setImage(byte[] image) {
//    }

//    public String getPhoto(){
//        return photo;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//    }
//        @Transient
//    public String getPhotosImagePath() {
//        if (photo == null || id == null) return null;
//
//        return "/collection-photos/" + id + "/" + photo;
//    }


}
