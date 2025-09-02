package isaeva.apigateway.service;

import isaeva.apigateway.config.UserServiceConfig;
import isaeva.apigateway.dto.UserRegistrationRequest;
import isaeva.apigateway.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceClient {


    private final WebClient.Builder webClientBuilder;
    private final UserServiceConfig userServiceConfig;

    public UserResponse createUser(UserRegistrationRequest request) {
        return webClientBuilder.build()
                .post()
                .uri(userServiceConfig.getUrl() + "/api/users")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }
}
