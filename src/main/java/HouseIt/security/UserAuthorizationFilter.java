package HouseIt.security;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Validates requests containing tokens from controller end points
 **/

@Component
public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(UserAuthorizationFilter.class);

    @Value("${security.token.expiration}")
    private Long expiration;

    @Value("${security.token.secret}")
    private String secret;

    @Value("${security.token.prefix}")
    private String tokenPrefix;

    @Value("${security.token.header}")
    private String tokenHeader;

    public UserAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // Parses the token and if valid places the user in the SecurityContext
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String header = request.getHeader(tokenHeader);

            if (header == null || !header.startsWith(tokenPrefix)) {
                chain.doFilter(request, response);
            }

            String user = null;

            if (header != null) {
                user = Jwts.parser()
                        .setSigningKey(secret.getBytes())
                        .parseClaimsJws(header.replace(tokenPrefix, ""))
                        .getBody()
                        .getSubject();
            }

            if (user != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( // principal, credential, authorities
                        user,
                        null,
                        new ArrayList<>()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

}