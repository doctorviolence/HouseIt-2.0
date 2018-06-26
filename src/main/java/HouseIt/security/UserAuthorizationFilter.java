package HouseIt.security;

import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Validates requests (w/ JWT) from controller end points
 **/

@Component
public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger logger = Logger.getLogger(UserAuthorizationFilter.class);

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

    public UserAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // Parses the token and if valid places the user in the SecurityContext
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String header = request.getHeader(tokenHeader);


            if (header != null) {
                String user = Jwts.parser()
                        .setSigningKey(secret.getBytes())
                        .parseClaimsJws(header.replace(tokenPrefix, ""))
                        .getBody()
                        .getSubject();

                if (user != null) {
                    UserDetails userDetails = userService.loadUserByUsername(user);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( // principal, credential, authorities
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
        }
    }

}