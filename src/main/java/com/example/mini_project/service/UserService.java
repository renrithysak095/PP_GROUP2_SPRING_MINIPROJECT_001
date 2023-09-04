package com.example.mini_project.service;

import com.example.mini_project.model.dto.UserDto;
import com.example.mini_project.model.request.BookmarkRequest;
import com.example.mini_project.model.request.UserRequest;
import com.example.mini_project.model.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

	UserDto addUser(UserRequest userRequest);

	PageResponse<List<UserDto>> getAllUsers(Integer pageNo, Integer pageSize);

	UserDto getUserById(UUID uuid);

	void deleteUserById(UUID uuid);

	UserDto updateUserById(UserRequest userRequest, UUID uuid);
}
