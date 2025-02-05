package org.ciopecalina.invoicingapp.assemblers;

import lombok.NonNull;
import org.ciopecalina.invoicingapp.dtos.UserRegistrationDto;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRegistrationAssembler implements RepresentationModelAssembler<User, UserRegistrationDto> {
    @Override
    public @NonNull UserRegistrationDto toModel(@NonNull User entity) {
        return new UserRegistrationDto(
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getFCode(),
                entity.getRegNo(),
                entity.getIban(),
                entity.getBank()
        );
    }

    public List<UserRegistrationDto> toModelList(List<User> entities) {
        return entities.stream().map(this::toModel).toList();
    }
}