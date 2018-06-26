import HouseIt.Application;
import HouseIt.config.DbConfig;
import HouseIt.config.WebSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Simply tests that the application runs without any exceptions
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, DbConfig.class, WebSecurityConfig.class})
public class ApplicationTest {

    @Test
    public void testWhenContextLoads_ThenNoExceptions() throws Exception {

    }

}
