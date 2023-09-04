package com.example.mini_project.service.implementation;

import com.example.mini_project.exception.NotFoundExceptionClass;
import com.example.mini_project.model.User;
import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.UserDto;
import com.example.mini_project.model.request.UserRequest;
import com.example.mini_project.model.response.PageResponse;
import com.example.mini_project.repository.UserRepository;
import com.example.mini_project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
		private  final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDto addUser(UserRequest userRequest) {
			var userEntity=userRequest.toEntity();
			return userRepository.save(userEntity).toDto();
	}

	@Override
	public PageResponse<List<UserDto>> getAllUsers(Integer pageNo, Integer pageSize) {
			Pageable pageable=PageRequest.of(pageNo,pageSize);
			Page<UserDto> pageResult= userRepository.findAll(pageable).map(User::toDto);
		return PageResponse.<List<UserDto>>builder()
				.message("successfully fetched article")
				.payload(pageResult.getContent())
				.status(HttpStatus.OK)
				.page(pageNo)
				.size(pageSize)
				.totalElement(pageResult.getTotalElements())
				.totalPages(pageResult.getTotalPages())
				.build();
	}


	@Override
	public UserDto getUserById(UUID uuid) {
		User user=userRepository.findById(uuid).orElseThrow(()-> new NotFoundExceptionClass("Id Not Found"));
		return userRepository.findById(uuid).get().toDto();
	}

	@Override
	public void deleteUserById(UUID uuid) {
		User user=userRepository.findById(uuid).orElseThrow(()-> new NotFoundExceptionClass("Id Not Found"));
		userRepository.deleteById(user.getId());
	}

	@Override
	public UserDto updateUserById(UserRequest userRequest, UUID uuid) {
		Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(uuid).orElseThrow(() -> new NotFoundExceptionClass("There is no data for this UUID")));
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// Update the user object with the new values from userRequest
			user.setName(userRequest.getName());
			user.setRole(userRequest.getRole());

			return userRepository.save(user).toDto();
		} else {
			// Handle the case where the user is not found
			throw new NotFoundException("User not found with UUID: " + uuid);
		}
	}


}
