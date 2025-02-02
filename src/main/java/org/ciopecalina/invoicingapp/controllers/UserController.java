package org.ciopecalina.invoicingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.assemblers.UserAssembler;
import org.ciopecalina.invoicingapp.assemblers.UserRegistrationAssembler;
import org.ciopecalina.invoicingapp.dtos.UserDto;
import org.ciopecalina.invoicingapp.dtos.UserRegistrationDto;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;
    private final UserRegistrationAssembler userRegistrationAssembler;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsersByIdOrderedDesc() {
        List<User> users = userService.getUsersOrderByIdDesc();
        return ResponseEntity.ok(userAssembler.toModelList(users));
    }

    @DeleteMapping("/users/{is}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
//        User newUser = userService.registerUser(userRegistrationDto);
//        return ResponseEntity.ok(newUser);
//    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationDto> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User newUser = userService.registerUser(userRegistrationDto);

        UserRegistrationDto userDto = userRegistrationAssembler.toModel(newUser);

        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/users/{id}/approve")
    public ResponseEntity<String> approveUser(@PathVariable Integer id) {
        boolean isApproved = userService.approveUser(id);

        if (isApproved) {
            return ResponseEntity.ok("User approved.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}