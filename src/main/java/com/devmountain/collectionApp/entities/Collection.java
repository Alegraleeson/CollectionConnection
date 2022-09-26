package com.devmountain.collectionApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.devmountain.collectionApp.dtos.CollectionDto;
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

//    @Column(table="Items" ,name="name")
//    private String item_name;
//
//    @Column(table="Items" ,name="brand")
//    private String brand;
//
//    @Column(table="Items" ,name="stock_photo")
//    private String stock_photo;
//
//    @Column(table="Items" ,name="original_price")
//    private Float original_price;



//    @Basic(fetch=FetchType.LAZY)
//    @Lob
//    @Column(table="items", name="keywords")
//    private String keywords;

//    @Basic(fetch=FetchType.LAZY)
//    @Lob
//    @Column(table="items", name="notes")
//    private String notes;

    @ManyToOne
    @JsonBackReference
    private User user;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @Column(name="items")
//    @JoinTable(
//            name = "items",
//            joinColumns = @JoinColumn(name = "collections_id"),
//            inverseJoinColumns = @JoinColumn(name = "items_id"))
//    private Set<Item> items;

//    @ManyToMany
//    @JoinTable(
//            name = "Items",
//            joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
//    private Set<Item> item;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH} )
    @JoinTable(
            name = "collection_items",
            joinColumns = @JoinColumn(
                    name = "collections", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "items", referencedColumnName = "id"
            )
    )
    private Set<Item> items = new HashSet<>();

    public Collection(CollectionDto collectionDto){
        if (collectionDto.getName() != null){
            this.name = collectionDto.getName();
        }


    }

    public Set<Item> getItems() {
        return items;
    }
    public void setItems(Set<Item> items) {
        this.items = items;
    }


//    public void addItem(Item item) {
//        this.items.add(item);
//        item.getCollections().add(this);
//    }
//
//    public void removeItem(long itemId) {
//        Item item = this.items.stream().filter(i -> i.getId() == itemId).findFirst().orElse(null);
//        if (item != null) {
//            this.items.remove(item);
//            item.getItems().remove();
//        }
//    }
}
