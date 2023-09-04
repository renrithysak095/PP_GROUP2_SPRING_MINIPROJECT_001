package com.example.mini_project.controller;
import com.example.mini_project.model.dto.UserDto;
import com.example.mini_project.model.request.UserRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Users")
public class UserController {

	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("")
	public ApiResponse<?> addNewUser(@RequestBody @Valid UserRequest userRequest) {
		if (userRequest == null || userRequest.getName() == null || userRequest.getName().isBlank()) {
			throw new IllegalArgumentException("User name cannot be blank");
		}

		var payload = userService.addUser(userRequest);
		return ApiResponse.<UserDto>builder()
				.message("Insert Successfully")
				.status(HttpStatus.OK)
				.payload(payload)
				.build();
	}

	@GetMapping("")
	public ResponseEntity<?> getAllUser(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize
	) {
			return  ResponseEntity.ok().body(userService.getAllUsers(pageNo,pageSize));

	}

	@GetMapping("/{id}")
	public ApiResponse<?>getUserById(@PathVariable("id") UUID uuid) {
		var findUser = userService.getUserById(uuid);

		if (findUser != null){
			return ApiResponse.<UserDto>builder()
					.message("Find Successfully")
					.payload(findUser)
					.status(HttpStatus.OK)
					.build();
	}else {
				throw  new NotFoundException("there is no data for this  UUID:" +uuid);
		}

	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable UUID id) {
		userService.deleteUserById(id);

		ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
				.message("deleted successfully")
				.payload(null)
				.status(HttpStatus.OK)
				.build();
		return ResponseEntity.ok().body(response);	}


	@PutMapping("/{id}")
	public ApiResponse<?>updateUser(@RequestBody UserRequest userRequest, @PathVariable("id") UUID uuid){
		if (userRequest == null || userRequest.getName() == null || userRequest.getName().isBlank()) {
			throw new IllegalArgumentException("User name cannot be blank");
		}
		var payload= userService.updateUserById(userRequest, uuid);
		return ApiResponse.<UserDto>builder()
				.message("update Successfully")
				.payload(payload)
				.status(HttpStatus.OK)
				.build();
	}
}
