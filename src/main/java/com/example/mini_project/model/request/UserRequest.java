package com.example.mini_project.model.request;

import com.example.mini_project.enumeration.Role;
import com.example.mini_project.model.Bookmark;
import com.example.mini_project.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
@NotEmpty
@NotBlank
public class UserRequest {

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    private Role role;
    public User toEntity(){
                return  new User(null,this.name,this.role);
            }
    public User toEntity(UUID uuid){
                return  new User(uuid,this.name, this.role);
            }

}
