package com.devmountain.collectionApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.devmountain.collectionApp.dtos.CollectionDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Collections")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String name;


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


    }


}
