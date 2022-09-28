package com.devmountain.collectionApp.dtos;

import com.devmountain.collectionApp.entities.Item;
import com.devmountain.collectionApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private Set<ItemDto> itemDtoSet = new HashSet<>();
    private Set<CollectionDto> collectionDtoSet = new HashSet<>();

    public UserDto(User user) {
        if (user.getId() != null){
            this.id = user.getId();
        }
        if (user.getUsername() != null){
            this.username = user.getUsername();
        }
        if (user.getPassword() != null){
            this.password = user.getPassword();
        }
        if (user.getFirst_name() != null){
            this.first_name = user.getFirst_name();
        }
        if (user.getLast_name() != null){
            this.last_name = user.getLast_name();
        }
        if (user.getEmail() != null){
            this.email = user.getEmail();
        }
    }
}
