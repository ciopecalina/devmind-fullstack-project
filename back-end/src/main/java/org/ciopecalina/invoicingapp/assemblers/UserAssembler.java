package org.ciopecalina.invoicingapp.assemblers;

import lombok.NonNull;
import org.ciopecalina.invoicingapp.dtos.UserDto;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, UserDto> {
    @Override
    public @NonNull UserDto toModel(@NonNull User entity) {
        UserDto userDto = new UserDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getIsApproved());

        return userDto;
    }

    public List<UserDto> toModelList(List<User> entities) {
        return entities.stream().map(this::toModel).toList();
    }
}