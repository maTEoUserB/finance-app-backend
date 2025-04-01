package pl.finances.finances_app.dto;

import pl.finances.finances_app.repositories.entities.UserEntity;

public record UserResponse(int id, UserEntity userEntity) {
}
