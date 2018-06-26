package HouseIt.security;

import HouseIt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = Logger.getLogger(UserAuthenticationFilter.class);

    @Value("${houseit.token.secret}")
    private String secret;

    @Value("${houseit.token.expiration}")
    private long expiration;

    @Value("${houseit.token.prefix}")
    private String tokenPrefix;

    @Value("${houseit.token.header}")
    private String tokenHeader;

    @Autowired
    private UserDetailsService userService;

    private AuthenticationManager authenticationManager;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // Authenticates user's credentials and, if it exists in DB, issues them to the Authentication Manager
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ServletInputStream json = (ServletInputStream) request.getInputStream();
            User user = new ObjectMapper().readValue(json, User.class);
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( // principal, credential, authorities
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()));

        } catch (IOException e) {
            logger.error(e);
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

        logger.debug(String.format("Successful authentication at %s", new UrlPathHelper().getPathWithinApplication(request)));
    }

    // If user's credentials fail then this method is called
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        logger.debug(String.format("Authentication failed at %s", new UrlPathHelper().getPathWithinApplication(request)));
    }

    // Generates JWT
    private String generateToken(Authentication authentication) {
        try {
            AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }

}