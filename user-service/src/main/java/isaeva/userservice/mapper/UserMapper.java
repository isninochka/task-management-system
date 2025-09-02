package isaeva.userservice.mapper;

import isaeva.userservice.dto.AuthResponse;
import isaeva.userservice.dto.UserRegistrationRequest;
import isaeva.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegistrationRequest request);

    @Mapping(target = "token", source = "token")
    AuthResponse toAuthResponse(User user);

}
