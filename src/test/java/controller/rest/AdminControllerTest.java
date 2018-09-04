package controller.rest;

import HouseIt.Application;
import HouseIt.entities.Apartment;
import HouseIt.entities.Building;
import HouseIt.entities.Tenant;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                    "http://localhost:" + port + "/buildings",
                    HttpMethod.GET,
                    request,
                    Building[].class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assert response.getBody() != null;
            for (Building b : response.getBody()) {
                String[] expected = {"Main St", "Pitt St", "Maroubra St"};
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
                    "http://localhost:" + port + "/buildings",
                    HttpMethod.GET,
                    request,
                    Building[].class
            );

            fail("Access not denied, did not return HTTP 401");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Test
    public void whenRequestingEntityById_thenReturnExpected() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);
            long id = 1;

            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity<Building> response = this.template.exchange(
                    "http://localhost:" + port + "/buildings/find-by-id/" + id,
                    HttpMethod.GET,
                    request,
                    Building.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assert response.getBody() != null;
            Building b = response.getBody();
            assertThat(b.getBuildingId()).isEqualTo(1);
            assertThat(b.getName()).isEqualTo("Test");
            assertThat(b.getAddress()).isEqualTo("Main St");
            assertThat(b.getZipCode()).isEqualTo("21536");
        } catch (HttpServerErrorException e) {
            fail("Did not return entity");
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

            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity<Apartment[]> response = this.template.exchange(
                    "http://localhost:" + port + "/apartments/apartments-in-building/" + id,
                    HttpMethod.GET,
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
    public void whenRequestingEmptyApartmentsInBuilding_thenReturnExpected() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);
            long id = 1;

            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity<Apartment[]> response = this.template.exchange(
                    "http://localhost:" + port + "/apartments/empty-apartments-in-building/" + id,
                    HttpMethod.GET,
                    request,
                    Apartment[].class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assert response.getBody() != null;
            Apartment[] apartments = response.getBody();
            assertThat(apartments.length).isEqualTo(1);
        } catch (HttpClientErrorException e) {
            logger.info(e.getMessage());
            fail("Apartments not retrieved, did not return HTTP 200");
        }
    }

    @Test
    public void whenRequestingApartmentsInBuildingWithNoParam_thenReturnNotFound() {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            HttpEntity request = new HttpEntity<>(null, headers);

            this.template.exchange(
                    "http://localhost:" + port + "/apartments/apartments-in-building",
                    HttpMethod.GET,
                    request,
                    Apartment[].class
            );

            fail("Did not return HTTP 400");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
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

            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity<Apartment[]> response = this.template.exchange(
                    "http://localhost:" + port + "/apartments/apartments-in-building/" + id,
                    HttpMethod.GET,
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
                    "http://localhost:" + port + "/apartments/create-apartment",
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
                    "http://localhost:" + port + "/apartments/create-apartment",
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
                    "http://localhost:" + port + "/apartments/update-apartment",
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
                    "http://localhost:" + port + "/apartments/update-apartment",
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
    public void whenDeletingEntity_thenReturnStatusCodeNoContent() throws JsonProcessingException {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            User u = new User("Test3", "password");
            HttpEntity request = new HttpEntity<>(new ObjectMapper().writeValueAsString(u), headers);

            ResponseEntity response = this.template.exchange(
                    "http://localhost:" + port + "/users/delete-user",
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
                    "http://localhost:" + port + "/buildings/delete-building/" + id,
                    HttpMethod.DELETE,
                    request,
                    Building.class
            );
            fail("Did not return HTTP 500");
        } catch (HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void whenCreatingUsers_thenReturnStatusCodeCreated() throws JsonProcessingException {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);

            User u = new User();
            u.setUsername("Test4");
            u.setPassword("Password");
            u.setRole("ROLE_TENANT");
            u.setTenant(new Tenant(1));
            u.setApartment(new Apartment(1));

            HttpEntity request = new HttpEntity<>(new ObjectMapper().writeValueAsString(u), headers);

            ResponseEntity response = this.template.exchange(
                    "http://localhost:" + port + "/users/create-user",
                    HttpMethod.POST,
                    request,
                    User.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        } catch (HttpServerErrorException e) {
            logger.info(e.getMessage());
            fail("Did not return HTTP 201");
        }
    }

    /*@Test
    public void whenUpdatingUserPassword_thenReturnNoException() throws JsonProcessingException {
        String token = initializeValidAuthorizationToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, token);
            HttpEntity request = new HttpEntity<>(headers);

            // Get original user password
            ResponseEntity<User> response = this.template.exchange(
                    "http://localhost:" + port + "/users/user",
                    HttpMethod.GET,
                    request,
                    User.class
            );
            assertThat(response).isNotNull();
            User user = response.getBody();
            assertThat(user).isNotNull();

            // Update user password
            HttpEntity request2 = new HttpEntity<>("test_new_password", headers);
            ResponseEntity response2 = this.template.exchange(
                    "http://localhost:" + port + "/users/update-user",
                    HttpMethod.PUT,
                    request2,
                    User.class
            );
            assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

            // Get changed user password
            ResponseEntity<User> response3 = this.template.exchange(
                    "http://localhost:" + port + "/users/user",
                    HttpMethod.GET,
                    request,
                    User.class
            );
            assertThat(response3).isNotNull();
            User user2 = response3.getBody();
            assertThat(user2).isNotNull();

            assertThat(user.getUsername().equals(user2.getUsername()));
            assertThat(user.getPassword()).isNotEqualTo(user2.getPassword());

            System.out.println("Username 1: " + user.getUsername() + " Username 2: " + user2.getUsername());
            System.out.println("Password 1: " + user.getPassword() + " Password 2: " + user2.getPassword());
        } catch (HttpServerErrorException e) {
            logger.error(e.getMessage());
            fail("Did not return HTTP 200");
        }
    }*/

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