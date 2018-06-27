package HouseIt.security;

import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Validates requests (w/ JWT) from controller end points
 **/

public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger logger = Logger.getLogger(UserAuthorizationFilter.class);

    private static String secret = "DrPepper5";
    private static long expiration = 604800;
    private static String tokenPrefix = "Bearer ";
    private static String tokenHeader = "Authorization";

    private UserDetailsService userService;

    public UserAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userService) {
        super(authenticationManager);
        this.userService = userService;
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

            assert header != null;
            String user = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(header.replace(tokenPrefix, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                UserDetails userDetails = userService.loadUserByUsername(user);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( // principal, credential, authorities
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
        }
    }

}