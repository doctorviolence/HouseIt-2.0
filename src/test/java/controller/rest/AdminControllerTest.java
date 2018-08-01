package controller.rest;

import HouseIt.Application;
import HouseIt.entities.Apartment;
import HouseIt.entities.Building;
import HouseIt.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class, TestDatabaseConfig.class})
@ActiveProfiles(profiles = "test")
public class AdminControllerTest {

    private final Logger logger = LoggerFactory.getLogger(AdminControllerTest.class);

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
        } catch (HttpClientErrorException e) {
            logger.info(e.getMessage());
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
                    "http://localhost:" + port + "admin/buildings",
                    HttpMethod.POST,
                    request,
                    Building[].class
            );

            fail("Access not denied, did not return HTTP 401");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Test
    public void whenRequestingApartmentsWithValidBuildingId_thenReturnExpected() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);
            long id = 1;

            HttpEntity request = new HttpEntity<>(id, headers); // ID sent in body because I will add more functionality to the method later

            ResponseEntity<Apartment[]> response = this.template.exchange(
                    "http://localhost:" + port + "/admin/apartments-in-building",
                    HttpMethod.POST,
                    request,
                    Apartment[].class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assert response.getBody() != null;
            Apartment[] apartments = response.getBody();
            assertThat(apartments.length).isEqualTo(2);
        } catch (HttpClientErrorException e) {
            logger.info(e.getMessage());
            fail("Apartments not retrieved, did not return HTTP 200");
        }
    }

    @Test
    public void whenRequestingApartmentsInBuildingWithNoBody_thenReturnBadRequest() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            HttpEntity request = new HttpEntity<>(null, headers);

            this.template.exchange(
                    "http://localhost:" + port + "/admin/apartments-in-building",
                    HttpMethod.POST,
                    request,
                    Apartment[].class
            );

            fail("Did not return HTTP 400");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void whenRequestingApartmentsInBuildingWithInvalidId_thenReturnEmptyArray() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);
            long id = 54;

            HttpEntity request = new HttpEntity<>(id, headers);

            ResponseEntity<Apartment[]> response = this.template.exchange(
                    "http://localhost:" + port + "/admin/apartments-in-building",
                    HttpMethod.POST,
                    request,
                    Apartment[].class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assert response.getBody() != null;
            Apartment[] apartments = response.getBody();
            assertThat(apartments.length == 0).isTrue();
        } catch (HttpClientErrorException e) {
            logger.info(e.getMessage());
            fail("Apartments found");
        }
    }

    @Test
    public void whenCreatingEntity_thenReturnStatusCodeCreated() throws JsonProcessingException {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            Apartment a = new Apartment();
            a.setApartmentNo("N303");
            a.setRent(5500);
            a.setSize(35);
            a.setFloorNo(3);
            a.setBuilding(new Building(1));

            HttpEntity request = new HttpEntity<>(new ObjectMapper().writeValueAsString(a), headers);

            ResponseEntity response = this.template.exchange(
                    "http://localhost:" + port + "/admin/create-apartment",
                    HttpMethod.POST,
                    request,
                    Apartment.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        } catch (HttpServerErrorException e) {
            logger.info(e.getMessage());
            fail("Did not return HTTP 201");
        }
    }

    @Test
    public void whenCreatingApartmentWithNoBuilding_thenReturnInternalServerError() throws JsonProcessingException {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            Apartment a = new Apartment();
            a.setApartmentNo("N303");
            a.setRent(5500);
            a.setFloorNo(3);
            a.setBuilding(null);

            HttpEntity request = new HttpEntity<>(new ObjectMapper().writeValueAsString(a), headers);

            this.template.exchange(
                    "http://localhost:" + port + "/admin/create-apartment",
                    HttpMethod.POST,
                    request,
                    Apartment.class
            );

            fail("Did not return HTTP 500");
        } catch (HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void whenUpdatingEntity_thenReturnStatusCodeOk() throws JsonProcessingException {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            Apartment a = new Apartment();
            a.setApartmentId(1);
            a.setApartmentNo("303");
            a.setRent(12500);
            a.setFloorNo(2);
            a.setBuilding(new Building(1));

            HttpEntity request = new HttpEntity<>(new ObjectMapper().writeValueAsString(a), headers);

            ResponseEntity response = this.template.exchange(
                    "http://localhost:" + port + "/admin/update-apartment",
                    HttpMethod.PUT,
                    request,
                    Apartment.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            logger.info(e.getMessage());
            fail("Did not return HTTP 200");
        }
    }

    @Test
    public void whenUpdatingInvalidEntity_thenReturnEntityNotFound() throws JsonProcessingException {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            Apartment a = new Apartment();
            a.setApartmentId(6);
            a.setBuilding(new Building(1));

            HttpEntity request = new HttpEntity<>(new ObjectMapper().writeValueAsString(a), headers);

            this.template.exchange(
                    "http://localhost:" + port + "/admin/update-apartment",
                    HttpMethod.PUT,
                    request,
                    Apartment.class
            );
            fail("Did not return HTTP 404");
        } catch (HttpClientErrorException e) {
            logger.info(e.getMessage());
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void whenDeletingEntity_thenReturnStatusCodeNoContent() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);
            long id = 3;

            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity response = this.template.exchange(
                    "http://localhost:" + port + "/admin/delete-user/" + id,
                    HttpMethod.DELETE,
                    request,
                    User.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        } catch (HttpServerErrorException e) {
            logger.info(e.getMessage());
            fail("Did not return HTTP 204");
        }
    }

    @Test
    public void whenDeletingEntityWithFkDependencies_thenReturnInternalServerError() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);
            long id = 1;

            HttpEntity request = new HttpEntity<>(headers);

            this.template.exchange(
                    "http://localhost:" + port + "/admin/delete-building/" + id,
                    HttpMethod.DELETE,
                    request,
                    Building.class
            );
            fail("Did not return HTTP 500");
        } catch (HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @Test
    public void whenRequestingWithMissingParameter_thenReturnMissingParameterException() {

    }*/

    private String initializeValidAuthorizationToken() {
        HttpHeaders headers = new HttpHeaders();
        String json = "{\"username\":\"Test\",\"password\":\"password\"}";
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        List<String> response = this.template.postForEntity("http://localhost:" + port + "/auth/login", entity, String.class)
                .getHeaders()
                .get("Authorization");

        assert response != null;
        return response.get(0); // wow, such ugly solution
    }

    private String initializeInvalidAuthorizationToken() {
        return "Bearer xxx.zzz.yyy";
    }

}