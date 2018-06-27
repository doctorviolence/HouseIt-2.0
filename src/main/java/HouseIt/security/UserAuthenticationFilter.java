package HouseIt.security;

import HouseIt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = Logger.getLogger(UserAuthenticationFilter.class);

    private static String secret = "DrPepper5";
    private static long expiration = 604800;
    private static String tokenPrefix = "Bearer ";
    private static String tokenHeader = "Authorization";

    private UserDetailsService userService;

    private AuthenticationManager authenticationManager;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        setFilterProcessesUrl("/login");
    }

    // Authenticates user's credentials and, if it exists in DB, issues them to the Authentication Manager
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ServletInputStream json = request.getInputStream();
            User user = new ObjectMapper().readValue(json, User.class);

            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

            return authenticationManager.authenticate( // look over this line
                    new UsernamePasswordAuthenticationToken( // principal, credential, authorities
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities())
            );

        } catch (AuthenticationException | IOException e) { // remember to change this
            logger.error(e);
            logger.debug(e);
            throw new RuntimeException(e);
        }
    }

    // If user's credentials are successful then this method is called by the filter
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth)
            throws IOException, ServletException {

        String token = generateToken(auth);

        if (token != null) {
            response.addHeader(tokenHeader, tokenPrefix + token);
        }

        logger.info(String.format("Successful authentication at %s", new UrlPathHelper().getPathWithinApplication(request)));
    }

    // If user's credentials fail then this method is called
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.format("Authentication failed at %s", new UrlPathHelper().getPathWithinApplication(request)));
        //logger.error(String.format("Authentication failed at %s", new UrlPathHelper().getPathWithinApplication(request)));
    }

    // Generates JWT
    private String generateToken(Authentication authentication) {
        try {
            AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                    .compact();
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }

}