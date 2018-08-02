package HouseIt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class WebCorsFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(WebCorsFilter.class);

    @Value("${security.token.header}")
    private String tokenHeader;

    @Value("${custom.cors.allowOrigin}")
    private String allowOrigin;

    @Value("${custom.cors.allowMethods}")
    private String allowMethods;

    @Value("${custom.cors.allowHeaders}")
    private String allowHeaders;

    @Value("${custom.cors.exposeHeaders}")
    private String exposeHeaders;

    @Value("${custom.cors.allowCredentials}")
    private String allowCredentials;

    @Value("${custom.cors.maxAge}")
    private String maxAge;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            response.addHeader("Access-Control-Allow-Origin", allowOrigin);
            response.addHeader("Access-Control-Allow-Methods", allowMethods);
            response.addHeader("Access-Control-Max-Age", maxAge);
            response.addHeader("Access-Control-Allow-Headers", allowHeaders + ", " + tokenHeader);
            response.addHeader("Access-Control-Expose-Headers", exposeHeaders);
            response.addHeader("Access-Control-Allow-Credentials", allowCredentials);

            chain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
