package com.example.mini_project.model;

import com.example.mini_project.enumeration.Role;
import com.example.mini_project.model.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks;

    public UserDto toDto(){
        return new UserDto(this.id, this.name, this.role);
    }

}
