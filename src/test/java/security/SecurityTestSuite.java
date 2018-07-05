package security;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserAuthenticationTest.class,
        UserAuthorizationTest.class,
        AuthenticatedUserTest.class
})
public class SecurityTestSuite {

}
