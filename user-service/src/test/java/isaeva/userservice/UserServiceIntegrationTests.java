package isaeva.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserServiceIntegrationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15").withDatabaseName("testdb")
            .withUsername("sa").withPassword("sa");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        r.add("spring.datasource.username", () -> postgres.getUsername());
        r.add("spring.datasource.password", () -> postgres.getPassword());
    }

    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate rest;

    @Test
    void registerAndLoginFlow() {
        String base = "http://localhost:" + port + "/auth";
        ResponseEntity<Map> reg = rest.postForEntity(base + "/register", Map.of("username", "tuser", "password", "pass", "email", "t@e"), Map.class);
        assertThat(reg.getStatusCode().is2xxSuccessful()).isTrue();
        ResponseEntity<Map> login = rest.postForEntity(base + "/login", Map.of("username", "tuser", "password", "pass"), Map.class);
        assertThat(login.getStatusCode().is2xxSuccessful()).isTrue();

    }
}
