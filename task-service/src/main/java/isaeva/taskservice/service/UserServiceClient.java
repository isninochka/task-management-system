package isaeva.taskservice.service;

import isaeva.taskservice.config.UserServiceConfig;
import isaeva.taskservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient.Builder webClientBuilder;
    private final UserServiceConfig userServiceConfig;

    public String getUsernameById(Long userId) {

        UserResponse user = webClientBuilder.build()
                .get()
                .uri(userServiceConfig.getUrl() + "/api/auth/" + userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();

        if (user == null) {
            throw new NotFoundException("User not found with id " + userId);
        }

        return user.username();
    }

    private record UserResponse(Long id, String username) {
    }
}