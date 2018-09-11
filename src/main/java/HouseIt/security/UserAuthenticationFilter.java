package HouseIt.security;

import HouseIt.entities.Apartment;
import HouseIt.entities.Tenant;
import HouseIt.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Authenticates user's credentials and, if the user exists in DB, issues them to the Authentication Manager
 **/

@Component
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    @Value("${security.token.expiration}")
    private Long expiration;

    @Value("${security.token.secret}")
    private String secret;

    @Value("${security.token.prefix}")
    private String tokenPrefix;

    @Value("${security.token.header}")
    private String tokenHeader;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        setFilterProcessesUrl("/auth/login");
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ServletInputStream json = request.getInputStream();
            User user = new ObjectMapper().readValue(json, User.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // If user's credentials are successful then this method is called by the filter
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        String token = this.generateToken(auth);
        Tenant tenant = this.generateUserTenant(auth);
        Apartment apartment = null;


        if (token != null) {
            response.addHeader(tokenHeader, tokenPrefix + token);
            response.getWriter().print("You are now logged in...");
        }

        if (tenant != null) {
            Long tenantId = tenant.getTenantId();
            apartment = tenant.getApartment();
            String tenantName = tenant.getFirstName();

            response.addHeader("Tenant", String.valueOf(tenantId));
            response.addHeader("Name", tenantName);
        }

        if (apartment != null) {
            Long apartmentId = apartment.getApartmentId();
            String apartmentNo = apartment.getApartmentNo();
            response.addHeader("ApartmentId", String.valueOf(apartmentId));
            response.addHeader("ApartmentNo", apartmentNo);

            Long buildingId = apartment.getBuilding().getBuildingId();
            String name = apartment.getBuilding().getName();
            response.addHeader("BuildingId", String.valueOf(buildingId));
            response.addHeader("BuildingName", name);
        }

        logger.info(String.format("Successful authentication at %s", new UrlPathHelper().getPathWithinApplication(request)));
    }

    // If user's credentials fail then this method is called
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.format("Bad credentials: Authentication failed at %s",
                new UrlPathHelper().getPathWithinApplication(request)));

        logger.error(String.format("Bad credentials: Authentication failed at %s", new UrlPathHelper().getPathWithinApplication(request)));
    }

    private String generateToken(Authentication authentication) {
        try {
            AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    private Tenant generateUserTenant(Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        Tenant tenant = user.getUser().getTenant();

        if (tenant == null) {
            return null;
        }

        return tenant;
    }

}