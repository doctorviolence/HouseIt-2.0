package service;

import HouseIt.Application;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;
import HouseIt.service.ITenantService;
import config.TestDatabaseConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class TenantServiceTest {

    @Autowired
    private ITenantService tenantService;

    @Test
    public void testThatTenantServiceIsNotNull() {
        assertThat(tenantService).isNotNull();
    }

    @Test
    public void whenFindingCasesByTenantId_ThenReturnTwoCases() throws MyEntityNotFoundException {
        List<Case> cases = tenantService.findCasesByTenantId(1);
        assertThat(cases).hasSize(2);
    }

    @Test
    public void whenFindingCasesByNo_ThenReturnOneCase() throws MyEntityNotFoundException {
        Case test = tenantService.findCase(1);
        assertThat(test.getCaseNo()).isEqualTo(1);
        assertThat(test.getCaseType()).isEqualTo("Kitchen");
        assertThat(test.getCaseType()).isNotEqualTo("Bathroom");
    }

    @Test
    public void whenFindingCasesByInvalidNo_ThenReturnNull() throws MyEntityNotFoundException {
        Case test = null;
        try {
            test = tenantService.findCase(3);
        } catch (MyEntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Case no. 3 not found.");
        }
        assertThat(test).isNull();
    }

    @Test
    public void whenGetMessagesByCase_thenReturnTwo() throws MyEntityNotFoundException {
        List<CaseMessage> caseMessages = tenantService.getCaseMessagesByCase(1);
        assertThat(caseMessages).hasSize(2);
    }

    @Test
    public void whenGetMessagesByInvalidCase_thenReturnNull() throws MyEntityNotFoundException {
        List<CaseMessage> caseMessages = tenantService.getCaseMessagesByCase(2);
        assertThat(caseMessages).isNullOrEmpty();
    }

}