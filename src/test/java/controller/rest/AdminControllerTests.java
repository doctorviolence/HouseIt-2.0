package controller.rest;

import HouseIt.Application;
import HouseIt.model.Building;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class, TestDatabaseConfig.class})
@ActiveProfiles(profiles = "test")
public class AdminControllerTests {

    private final Logger logger = LoggerFactory.getLogger(AdminControllerTests.class);

    private RestTemplate template;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        this.template = new RestTemplate();
    }

    @After
    public void tearDown() throws Exception {
        this.template = null;
    }

    @Test
    public void whenMakingValidRequest_thenReturnExpected() {
        String token = initializeValidAuthorizationToken();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<Building[]> response = template.exchange(
                    "http://localhost:" + port + "/admin/buildings",
                    HttpMethod.POST,
                    request,
                    Building[].class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assert response.getBody() != null;
            for (Building b : response.getBody()) {
                String[] expected = {"Hyllie allé", "Drottninggatan", "Emporia"};
                String[] notExpected = {"Sydney", "London", "Berlin"};
                String actual = b.getAddress();

                assertThat(Arrays.stream(expected).parallel().anyMatch(actual::contains)).isTrue();
                assertThat(Arrays.stream(notExpected).parallel().anyMatch(actual::contains)).isFalse();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail("Access denied, did not return HTTP 200");
        }
    }

    @Test
    public void whenRequestingWithInvalidToken_thenReturnForbidden() {
        String invalidToken = initializeInvalidAuthorizationToken();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, invalidToken);

            HttpEntity<String> request = new HttpEntity<>(headers);

            this.template.exchange(
                    "http://localhost:" + port + "/admin/buildings",
                    HttpMethod.POST,
                    request,
                    Building[].class
            );

            fail("Access not denied, did not return HTTP 403");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        }
    }

    @Test
    public void whenRequestingInvalidEntity_thenReturnEntityNotFoundException() {
        // TO-DO
    }

    @Test
    public void whenRequestingDataConstraintViolation_thenReturnDatabaseIntegrityViolation() {
        // TO-DO
    }

    @Test
    public void whenRequestingWithMissingParameter_thenReturnMissingParameterException() {
        // TO-DO
    }

    private String initializeValidAuthorizationToken() {
        HttpHeaders headers = new HttpHeaders();
        String json = "{\"username\":\"Test\",\"password\":\"password\"}";
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        List<String> response = this.template.postForEntity("http://localhost:" + port + "/login", entity, String.class)
                .getHeaders()
                .get("Authorization"); // wow, such ugly solution

        assert response != null;
        return response.get(0);
    }

    private String initializeInvalidAuthorizationToken() {
        return "Bearer xxx.zzz.yyy";
    }

}