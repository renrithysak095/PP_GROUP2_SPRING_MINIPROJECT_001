package com.example.mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {

    private String message;
    private HttpStatus status;
    private T payload;

}
