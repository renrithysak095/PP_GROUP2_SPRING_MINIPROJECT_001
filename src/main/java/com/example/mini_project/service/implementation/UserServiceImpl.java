package com.example.mini_project.service.implementation;
import com.example.mini_project.model.User;
import com.example.mini_project.model.dto.UserDto;
import com.example.mini_project.model.response.PageResponse;
import com.example.mini_project.repository.UserRepository;
import com.example.mini_project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageResponse<List<UserDto>> getAllArticles(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> pageResult = userRepository.findAll(pageable).map(User::toDto);

        return PageResponse.<List<UserDto>>builder()
                .message("successfully fetched user")
                .payload(pageResult.getContent())
                .status(HttpStatus.OK)
                .page(page)
                .size(size)
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }
}
