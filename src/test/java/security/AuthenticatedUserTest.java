package security;

import HouseIt.entities.Apartment;
import HouseIt.entities.Building;
import HouseIt.entities.Tenant;
import HouseIt.entities.User;
import HouseIt.security.AuthenticatedUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticatedUserTest {

    private User user = new User();
    private User user2 = new User();
    private final String username = "Test";
    private final String password = "Password";
    private final Tenant tenant = new Tenant(12);
    private final Tenant tenant2 = new Tenant(69);
    private final Apartment apartment = new Apartment(1);
    private final Building building = new Building(1);
    private final Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_TENANT");

    @Before
    public void setUp() throws Exception {
        this.user.setPassword(this.password);
        this.user.setUsername(this.username);
        this.user.setRole("ROLE_ADMIN,ROLE_TENANT");
        this.user.setTenant(this.tenant);
        this.apartment.setBuilding(this.building);
        this.user.setApartment(this.apartment);
        this.user2.setPassword(this.password);
        this.user2.setUsername(this.username);
        this.user2.setRole("ROLE_ADMIN,ROLE_TENANT");
        this.user2.setTenant(this.tenant2);
        this.apartment.setBuilding(this.building);
        this.user2.setApartment(this.apartment);
    }

    @After
    public void tearDown() throws Exception {
        this.user = null;
    }

    @Test
    public void whenCallingAuthenticatedUserGetters_thenReturnCorrectValues() {
        AuthenticatedUser authUser = new AuthenticatedUser(username, password, this.user);

        assertThat(authUser).isNotNull();
        assertThat(authUser.getUsername()).isEqualTo(username);
        assertThat(authUser.getPassword()).isEqualTo(password);
        assertThat(authUser.getUser()).isEqualTo(this.user);
        assertThat(authUser.getAuthorities()).isEqualTo(authorities);
        assertThat(authUser.isEnabled()).isTrue();
        assertThat(authUser.isAccountNonExpired()).isTrue();
        assertThat(authUser.isAccountNonLocked()).isTrue();
        assertThat(authUser.isCredentialsNonExpired()).isTrue();
    }

    @Test
    public void whenUpdatingUserRole_thenReturnCorrectAuthorities() {
        AuthenticatedUser authUser = new AuthenticatedUser(username, password, this.user);
        assertThat(authUser.getAuthorities()).isEqualTo(authorities);

        this.user.setRole("ROLE_TENANT");
        assertThat(authUser.getAuthorities()).isNotEqualTo(authorities);
    }

    @Test
    public void whenInsertingWrongRoleToUser_thenReturnNoAuthoritiesFromAuthUser() {
        this.user.setRole("WRONG");
        AuthenticatedUser authUser = new AuthenticatedUser(username, password, this.user);

        assertThat(authUser.getAuthorities()).isEmpty();
    }

    @Test
    public void whenGettingTenantObjectFromAuthUser_thenReturnCorrectTenantId() {
        AuthenticatedUser authUser = new AuthenticatedUser(username, password, this.user2);
        assertThat(authUser.getTenant().getTenantId()).isEqualTo(69);
    }

    @Test
    public void whenGettingApartmentObjectFromAuthUser_thenReturnCorrectApartmentId() {
        AuthenticatedUser authUser = new AuthenticatedUser(username, password, this.user2);
        assertThat(authUser.getApartment().getApartmentId()).isEqualTo(1);
    }

}