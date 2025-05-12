package pl.finances.finances_app.dto.requestAndResponse;

import pl.finances.finances_app.repositories.entities.UserEntity;

public record UserResponse(long id, UserEntity userEntity) {
}
