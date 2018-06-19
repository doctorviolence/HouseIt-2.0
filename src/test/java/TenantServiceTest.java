import HouseIt.Application;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;
import HouseIt.service.ITenantService;
import config.DbTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests against tenant service layer
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, DbTestConfig.class})
@ActiveProfiles("test")
public class TenantServiceTest {

    @Autowired
    private ITenantService tenantService;

    @Test
    public void testThatTenantServiceIsNotNull() {
        assertThat(tenantService).isNotNull();
    }

    @Test
    public void testWhenFindingCasesByTenantId_ThenReturnTwoCases() {
        List<Case> cases = tenantService.findCasesByTenantId(1);
        assertThat(cases).hasSize(2);
    }

    @Test
    public void testWhenFindingCasesByNo_ThenReturnOneCase() {
        Case test = tenantService.findCase(1);
        assertThat(test.getCaseNo()).isEqualTo(1);
        assertThat(test.getCaseType()).isEqualTo("Kitchen");
        assertThat(test.getCaseType()).isNotEqualTo("Bathroom");
    }

    @Test
    public void testWhenFindingCasesByInvalidNo_ThenReturnNull() {
        Case test = tenantService.findCase(3);
        assertThat(test).isNull();
    }

    @Test
    public void testWhenGetMessagesByCase_thenReturnTwo() {
        List<CaseMessage> caseMessages = tenantService.getCaseMessagesByCase(1);
        assertThat(caseMessages).hasSize(2);
    }

    @Test
    public void testWhenGetMessagesByInvalidCase_thenReturnNull() {
        List<CaseMessage> caseMessages = tenantService.getCaseMessagesByCase(2);
        assertThat(caseMessages).isNullOrEmpty();
    }

}