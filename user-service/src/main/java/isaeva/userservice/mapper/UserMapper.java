package isaeva.userservice.mapper;

import isaeva.userservice.dto.AuthResponse;
import isaeva.userservice.dto.UserRegistrationRequest;
import isaeva.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegistrationRequest request);

    AuthResponse toAuthResponse(User user, String token);

}
