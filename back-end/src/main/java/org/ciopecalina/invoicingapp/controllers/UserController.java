package org.ciopecalina.invoicingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.assemblers.UserAssembler;
import org.ciopecalina.invoicingapp.dtos.UserDto;
import org.ciopecalina.invoicingapp.dtos.UserRegistrationDto;
import org.ciopecalina.invoicingapp.dtos.UserResponseDto;
import org.ciopecalina.invoicingapp.dtos.UserSecurityDto;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping("admin/all")
    public ResponseEntity<List<UserDto>> getUsersByIdOrderedDesc() {
        List<User> users = userService.getUsersOrderByIdDesc();
        return ResponseEntity.ok(userAssembler.toModelList(users));
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
        return ResponseEntity.ok(new UserResponseDto(user.getEmail(), user.getEmail(), user.getIsApproved()));
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
}