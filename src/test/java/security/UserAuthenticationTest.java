package security;

import HouseIt.Application;
import HouseIt.config.WebSecurityConfig;
import config.TestDatabaseConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.jvm.hotspot.utilities.AssertionFailure;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {Application.class, WebSecurityConfig.class, TestDatabaseConfig.class})
@ActiveProfiles(profiles = "test")
public class UserAuthenticationTest {

    private final Logger logger = LoggerFactory.getLogger(UserAuthenticationTest.class);

    @Value("${security.token.secret}")
    private String secret;

    @Value("${security.token.prefix}")
    private String tokenPrefix;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        this.mvc = null;
    }

    @Test
    public void whenAuthenticatingWithCorrectCredentials_thenReturnToken() throws Exception {
        String json = "{\"username\":\"Test\",\"password\":\"password\"}";

        String token = this.mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader("Authorization");

        try {
            Claims claims = parseTokenClaims(token);
            assertThat(claims).isNotNull();

            String username = claims.getSubject();
            assertThat(username).isEqualTo("Test");

            Date expiration = claims.getExpiration();
            assertThat(expiration).isNotNull();

        } catch (Exception e) {
            fail("Failed to parse claims from token");
        }
    }

    @Test
    public void whenAuthenticatingWithIncorrectCredentials_thenReturnAccessDenied() throws Exception {
        String json = "{\"username\":\"Test\",\"password\":\"Wrong\"}";

        MvcResult result = this.mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Bad credentials")))
                .andReturn();

        try {
            assertThat(result.getResponse().getHeader("Authorization")).isNull();
        } catch (Exception e) {
            fail("Token was generated nonetheless");
        }
    }

    @Test
    public void whenAuthenticatingWithNoCredentials_thenReturnAccessDenied() throws Exception {
        String json = "{\"username\":\"\",\"password\":\"\"}";

        MvcResult result = this.mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Bad credentials")))
                .andReturn();

        try {
            assertThat(result.getResponse().getHeader("Authorization")).isNull();
        } catch (Exception e) {
            fail("Token was generated nonetheless");
        }
    }

    // NB: Adding more tests (e.g. expiration tests) once I have re-written the service layer/rest endpoints

    private Claims parseTokenClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secret.getBytes("UTF-8"))
                    .parseClaimsJws(token.replace(tokenPrefix, ""))
                    .getBody();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}