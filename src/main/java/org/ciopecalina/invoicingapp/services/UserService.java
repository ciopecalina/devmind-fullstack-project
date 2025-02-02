package org.ciopecalina.invoicingapp.services;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.UserRegistrationDto;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final StockProductRepository stockProductRepository;
//    private final InvoiceRepository invoiceRepository;
//    private final InvoiceProductRepository invoiceProductRepository;

    public List<User> getUsersOrderByIdDesc() {
        return userRepository.findAllByIsAdminFalseOrderByIdDesc();
    }

    @Transactional
    public boolean deleteUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

//            invoiceRepository.deleteByUser(user);
//            stockProductRepository.deleteByUser(user);
//            invoiceProductRepository.deleteByUser(user);

            userRepository.delete(user);

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
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        Optional<User> existingUser = userRepository
                .findByNameIsContainingIgnoreCaseAndEmail(userRegistrationDto.getName(), userRegistrationDto.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }

        User newUser = new User();
        newUser.setEmail(userRegistrationDto.getEmail());
        newUser.setPassword(userRegistrationDto.getPassword());
        newUser.setName(userRegistrationDto.getName());
        newUser.setFCode(userRegistrationDto.getFCode());
        newUser.setRegNo(userRegistrationDto.getRegNo());
        newUser.setIban(userRegistrationDto.getIban());
        newUser.setBank(userRegistrationDto.getBank());
        newUser.setIsApproved(false);
        newUser.setIsAdmin(false);

        return userRepository.save(newUser);
    }
}
