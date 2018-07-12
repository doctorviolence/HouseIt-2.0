package security;

import HouseIt.Application;
import HouseIt.config.WebSecurityConfig;
import HouseIt.model.Building;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {Application.class, WebSecurityConfig.class, TestDatabaseConfig.class})
@ActiveProfiles(profiles = "test")
public class UserAuthorizationTest {

    private final Logger logger = LoggerFactory.getLogger(UserAuthorizationTest.class);

    private MockMvc mvc;
    private String adminToken;
    private String tenantToken;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

        this.adminToken = this.mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"username\":\"Test\",\"password\":\"password\"}"))
                .andReturn()
                .getResponse()
                .getHeader("Authorization");

        this.tenantToken = this.mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"username\":\"Test3\",\"password\":\"password\"}"))
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
    }

    @After
    public void tearDown() throws Exception {
        this.mvc = null;
        this.adminToken = null;
        this.tenantToken = null;
    }

    @Test
    public void testThatTokensAreNotNull() {
        assertThat(this.adminToken).isNotNull();
        assertThat(this.tenantToken).isNotNull();
    }

    @Test
    public void whenRequestingAdminRestEndPointWithNoToken_thenReturnInvalidToken() {
        try {
            this.mvc.perform(post("/admin/buildings"));

        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Invalid token");
        }
    }

    @Test
    public void whenRequestingAdminRestEndPointWithValidToken_thenReturnExpected() {
        try {
            String result = this.mvc.perform(post("/admin/buildings")
                    .header("Authorization", this.adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            Building[] buildings = new ObjectMapper().readValue(result, Building[].class);
            assertThat(buildings).isNotNull();
            assertThat(buildings.length).isEqualTo(3);

        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            fail("Access denied");
        }
    }

    @Test
    public void whenRequestingAdminRestEndPointWithInvalidToken_thenReturnUnauthorized() {
        try {
            this.mvc.perform(post("/admin/buildings")
                    .header("Authorization", this.tenantToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());

        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            fail("HTTP 401 not returned");
        }
    }

    @Test
    public void whenRequestingTenantEndPointWithValidAdminToken_thenReturnStatusCodeOk() {
        try {
            this.mvc.perform(post("/tenant/tasks/1")
                    .header("Authorization", this.adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            fail("Access denied");
        }
    }

    @Test
    public void whenRequestingTenantEndPointWithValidTenantToken_thenReturnStatusCodeOk() {
        try {
            this.mvc.perform(post("/tenant/messages/1")
                    .header("Authorization", this.tenantToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            fail("Access denied");
        }
    }

}