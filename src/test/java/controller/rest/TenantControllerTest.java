package controller.rest;

import HouseIt.Application;
import HouseIt.entities.Task;
import config.TestDatabaseConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class, TestDatabaseConfig.class})
@ActiveProfiles(profiles = "test")
public class TenantControllerTest {

    private final Logger logger = LoggerFactory.getLogger(TenantControllerTest.class);

    private RestTemplate template;
    private String tenantAuth = "{\"username\":\"Test3\",\"password\":\"password\"}";
    private String tenantToken;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        this.template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(this.tenantAuth, headers);

        List<String> response = this.template.postForEntity("http://localhost:" + port + "/auth/login", entity, String.class)
                .getHeaders()
                .get("Authorization");

        assert response != null;
        this.tenantToken = response.get(0);
    }

    @After
    public void tearDown() throws Exception {
        this.tenantToken = null;
        this.template = null;
    }

    @Test
    public void whenRequestingTasksWithValidTenantId_thenReturnExpected() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, this.tenantToken);

        try {
            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity<Task[]> response = this.template.exchange(
                    "http://localhost:" + port + "/tasks-by-tenant/" + 1,
                    HttpMethod.POST,
                    request,
                    Task[].class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assert response.getBody() != null;
            Task[] tasks = response.getBody();

            assertThat(tasks.length).isEqualTo(2);

        } catch (HttpClientErrorException e) {
            logger.info(e.getMessage());
            fail("Tasks not retrieved, did not return HTTP 200");
        }
    }

    @Test
    public void whenRequestingTasksWithInvalidTenantId_thenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, this.tenantToken);

        try {
            HttpEntity request = new HttpEntity<>(headers);

            this.template.exchange(
                    "http://localhost:" + port + "/tasks-by-tenant/" + 65,
                    HttpMethod.POST,
                    request,
                    Task[].class
            );

            fail("Did not return HTTP 404");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void whenRequestingTasksWithInvalidParameter_thenReturnBadRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, this.tenantToken);

        try {
            HttpEntity request = new HttpEntity<>(headers);

            this.template.exchange(
                    "http://localhost:" + port + "/tasks-by-tenant/" + "bad-value",
                    HttpMethod.POST,
                    request,
                    Task[].class
            );

            fail("Did not return HTTP 400");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    /*@Test
    public void whenRequestingTasksWithNoParameter_thenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, this.tenantToken);

        try {
            HttpEntity request = new HttpEntity<>(headers);

            this.template.exchange(
                    "http://localhost:" + port + "/tasks",
                    HttpMethod.POST,
                    request,
                    Task[].class
            );

            fail("Did not return HTTP 404");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }*/

    @Test
    public void whenRequestingTasksWithNoToken_thenReturnUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity request = new HttpEntity<>(headers);

            this.template.exchange(
                    "http://localhost:" + port + "/tasks/" + 1,
                    HttpMethod.POST,
                    request,
                    Task[].class
            );

            fail("Did not return HTTP 401");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

}