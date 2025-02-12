package org.ciopecalina.invoicingapp.services;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.UserDetailsDto;
import org.ciopecalina.invoicingapp.dtos.UserRegistrationDto;
import org.ciopecalina.invoicingapp.dtos.UserResponseDto;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDetailsDto> getUserDetailsByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new UserDetailsDto(
                        user.getId(),
                        user.getName(),
                        user.getFCode(),
                        user.getRegNo(),
                        user.getIban(),
                        user.getBank()
                ));
    }

    public List<User> getUsersOrderByIdDesc() {
        return userRepository.findAllByIsAdminFalseOrderByIdDesc();
    }

    @Transactional
    public boolean deleteUserById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean approveUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsApproved(true);

            userRepository.save(user);

            return true;
        }
        return false;
    }

    @Transactional
    public UserResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }

        User newUser = new User();
        newUser.setEmail(userRegistrationDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        newUser.setName(userRegistrationDto.getName());
        newUser.setFCode(userRegistrationDto.getFCode());
        newUser.setRegNo(userRegistrationDto.getRegNo());
        newUser.setIban(userRegistrationDto.getIban());
        newUser.setBank(userRegistrationDto.getBank());
        newUser.setIsApproved(false);
        newUser.setIsAdmin(false);
        newUser.setId(null);

        User savedUser = userRepository.save(newUser);

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getName(), savedUser.getIsApproved(), savedUser.getIsAdmin());
    }
}
