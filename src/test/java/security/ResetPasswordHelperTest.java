package security;

import HouseIt.security.ResetPasswordHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResetPasswordHelperTest {

    private ResetPasswordHelper helper;
    private ResetPasswordHelper helper2;
    private String oldPassword;
    private String newPassword;

    @Before
    public void setUp() throws Exception {
        this.oldPassword = "password";
        this.newPassword = "password2";
    }

    @After
    public void tearDown() throws Exception {
        this.oldPassword = null;
        this.newPassword = null;
        this.helper = null;
        this.helper2 = null;
    }

    @Test
    public void whenCallingResetPasswordHelper_thenReturnExpected() {
        this.helper = new ResetPasswordHelper(oldPassword, newPassword);
        assertThat(this.helper.getOldPassword()).isEqualTo(this.oldPassword);
        assertThat(this.helper.getNewPassword()).isEqualTo(this.newPassword);
        assertThat(this.helper.getOldPassword()).isNotEqualTo(this.newPassword);
        assertThat(this.helper.getOldPassword()).isNotEqualTo("password3");
    }

    @Test
    public void whenTestingIfPasswordsAreEqual_thenReturnFalse() {
        this.helper = new ResetPasswordHelper(oldPassword, newPassword);
        boolean isFalse = this.helper.checkIfPasswordsAreDifferent();
        assertThat(isFalse).isTrue();

        this.helper2 = new ResetPasswordHelper("Test", "Test");
        boolean isTrue = this.helper2.checkIfPasswordsAreDifferent();
        assertThat(isTrue).isFalse();
    }

    @Test
    public void whenPassingJsonDataToResetPasswordHelper_thenReturnExpected() throws IOException {
        String json = "{\"oldPassword\":\"password\",\"newPassword\":\"password2\"}";
        ResetPasswordHelper jsonHelper = new ObjectMapper().readValue(json, ResetPasswordHelper.class);
        assertThat(jsonHelper.getOldPassword()).isEqualTo("password");
        assertThat(jsonHelper.getNewPassword()).isEqualTo("password2");
    }

}
