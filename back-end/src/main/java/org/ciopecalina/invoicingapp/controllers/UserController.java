package org.ciopecalina.invoicingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.*;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("admin/all")
    public ResponseEntity<List<UserApprovalDto>> getAllUsers() {
        List<User> users = userService.getUsersByIsAdminFalse();

        List<UserApprovalDto> userDtos = users.stream()
                .map(user -> new UserApprovalDto(user.getId(), user.getName(), user.getEmail(), user.getIsApproved()))
                .toList();

        return ResponseEntity.ok(userDtos);
    }


    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        UserResponseDto userDto = userService.registerUser(userRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getAuthenticatedUser(@AuthenticationPrincipal UserSecurityDto user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getUsername(), user.getName(), user.getIsApproved(), user.getIsAdmin()));
    }

    @PutMapping("admin/approve/{id}")
    public ResponseEntity<String> approveUser(@PathVariable Integer id) {
        boolean isApproved = userService.approveUser(id);

        if (isApproved) {
            return ResponseEntity.ok("User approved.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("users/get-user-details/{email}")
    public ResponseEntity<UserDetailsDto> getUserByEmail(@PathVariable String email) {
        System.out.println("Received request for email: " + email);

        Optional<UserDetailsDto> userDto = userService.getUserDetailsByEmail(email);
        return userDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clients/{userName}")
    public ResponseEntity<List<String>> getClientNames(@PathVariable String userName) {
        List<String> clientNames = userService.getClientNames(userName);
        return ResponseEntity.ok(clientNames);
    }

}