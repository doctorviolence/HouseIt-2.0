package HouseIt.security;

import HouseIt.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * I am using a custom principal object here in order to return attributes from my domain user
 **/

public class AuthenticatedUser implements UserDetails {

    private User user;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;

    public AuthenticatedUser() {
        super();
    }

    public AuthenticatedUser(String username, String password, User user) {
        this.setUser(user);
        this.setUsername(username);
        this.setPassword(password);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}