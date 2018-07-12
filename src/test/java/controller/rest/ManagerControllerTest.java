package controller.rest;

import HouseIt.Application;
import config.TestDatabaseConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class, TestDatabaseConfig.class})
@ActiveProfiles(profiles = "test")
public class ManagerControllerTest {

    private final Logger logger = LoggerFactory.getLogger(ManagerControllerTest.class);

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
    public void whenRequestingWithManagerToken_thenReturnExpected() {
        // TO-DO
    }

    @Test
    public void whenRequestingWithAdminToken_thenReturnExpected() {
        // TO-DO
    }

    @Test
    public void whenRequestingWithTenantToken_thenReturnUnauthorized() {
        // TO-DO
    }

    @Test
    public void whenRequestingWithNoToken_thenReturnUnauthorized() {
        // TO-DO
    }

}
