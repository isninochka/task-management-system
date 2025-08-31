package isaeva.taskservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient.Builder webClientBuilder;

    public String getUsernameById(Long userId) {

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/auth/"+userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(UserResponse::username)
                .block();

    }
    private record UserResponse(Long id, String username) {}

}
