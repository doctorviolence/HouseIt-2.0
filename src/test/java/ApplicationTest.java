import HouseIt.Application;
import HouseIt.config.DatabaseConfig;
import HouseIt.config.WebSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, DatabaseConfig.class, WebSecurityConfig.class})
public class ApplicationTest {

    @Test
    public void whenLoadApplication_ThenNoExceptions() throws Exception {

    }

}