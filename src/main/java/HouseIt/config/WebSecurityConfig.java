package HouseIt.config;

import HouseIt.security.MyAccessDeniedHandler;
import HouseIt.security.MyAuthenticationEntryPoint;
import HouseIt.security.UserAuthenticationFilter;
import HouseIt.security.UserAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public UserAuthenticationFilter userAuthenticationFilterBean() throws Exception {
        return new UserAuthenticationFilter(authenticationManagerBean());
    }

    @Bean
    public UserAuthorizationFilter userAuthorizationFilterBean() throws Exception {
        return new UserAuthorizationFilter(authenticationManagerBean());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandlerBean() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPointBean() {
        return new MyAuthenticationEntryPoint();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                    .and()
                    .csrf()
                    .disable() // Disabling CSRF because session management is stateless/no cookies
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .anyRequest()
                    .authenticated();

        // This adds my custom filter chain for authentication/authorization
        httpSecurity
                .addFilterBefore(userAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(userAuthorizationFilterBean(), BasicAuthenticationFilter.class);

        // Adding my custom exception handling to the filter chain
        httpSecurity
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPointBean())
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandlerBean());
    }

}