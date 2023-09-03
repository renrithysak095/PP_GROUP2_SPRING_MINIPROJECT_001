package com.example.mini_project.service;
import com.example.mini_project.model.dto.UserDto;
import com.example.mini_project.model.response.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    PageResponse<List<UserDto>> getAllArticles(Integer page, Integer size);

}
