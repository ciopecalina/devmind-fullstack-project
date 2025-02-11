package org.ciopecalina.invoicingapp.services;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.UserRegistrationDto;
import org.ciopecalina.invoicingapp.dtos.UserResponseDto;
import org.ciopecalina.invoicingapp.dtos.UserSecurityDto;
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

    public List<User> getUsersOrderByIdDesc() {
        return userRepository.findAllByIsAdminFalseOrderByIdDesc();
    }

    public Optional<UserSecurityDto> getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> new UserSecurityDto(user.getEmail(), user.getPassword(), user.getIsApproved(), user.getIsAdmin()));
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

        User savedUser = userRepository.save(newUser);

        return new UserResponseDto(savedUser.getName(), savedUser.getIsApproved(), savedUser.getIsAdmin());
    }
}
