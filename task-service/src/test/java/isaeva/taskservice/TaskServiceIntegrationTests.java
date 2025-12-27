package isaeva.taskservice;

import isaeva.taskservice.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class TaskServiceIntegrationTests {

  @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName("testdb").withUsername("postgres").withPassword("postgres");

  @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
      registry.add("spring.datasource.url", postgres::getJdbcUrl);
      registry.add("spring.datasource.username", postgres::getPassword);
      registry.add("spring.datasource.password", postgres::getPassword);
  }

  @LocalServerPort
    int port;
  @Autowired
  TestRestTemplate restTemplate;

  @Test
    void getAllTasks() {
      String base = "http://localhost:" + port + "/tasks";
      Task t = new Task();
      t.setTitle("t1");
      t.setDescription("d");
      ResponseEntity<Task> res = restTemplate.postForEntity(base, t, Task.class);
      assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
      Task created = res.getBody();
      assertThat(created.getId()).isNotNull();
      ResponseEntity<Task[]> list = restTemplate.getForEntity(base, Task[].class);
      assertThat(list.getBody()).isNotEmpty();
  }


}
