package service;

import HouseIt.Application;
import HouseIt.model.*;
import HouseIt.service.IManagerService;
import config.TestDatabaseConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests against manager service layer
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class ManagerServiceTest {

    @Autowired
    private IManagerService managerService;

    @Test
    public void testThatManagerServiceIsNotNull() {
        assertThat(managerService).isNotNull();
    }

    @Test
    public void whenGettingAllBuildings_ThenReturnThree() {
        List<Building> buildings = managerService.getBuildings();
        assertThat(buildings).hasSize(3);
    }

    @Test
    public void whenGettingTenantsInApartment_ThenReturnOne() {
        List<Tenant> tenants = managerService.getTenantsInApartment(1);
        assertThat(tenants).hasSize(1);
    }

    @Test
    public void whenGettingTenantsInInvalidApartment_ThenReturnNone() {
        List<Tenant> tenants = managerService.getTenantsInApartment(111);
        assertThat(tenants).isNullOrEmpty();
    }

    @Test
    public void whenFindingCasesByTenantId_ThenReturnTwo() {
        List<Case> cases = managerService.findCasesByTenantId(1);
        assertThat(cases.size() == 2);
    }

    @Test
    public void whenFindingCasesByInvalidTenantId_ThenReturnNone() {
        List<Case> cases = managerService.findCasesByTenantId(111);
        assertThat(cases).isNullOrEmpty();
    }

    @Test
    public void whenGettingCaseByType_ThenReturnCorrectCases() {
        List<Case> water = managerService.getCasesByType("Water");
        assertThat(water).isNullOrEmpty();

        List<Case> kitchen = managerService.getCasesByType("Kitchen");
        assertThat(kitchen).hasSize(1);

        Case c = kitchen.get(0);
        assertThat(c.getCaseNo()).isEqualTo(1);
        assertThat(c.getCaseType()).isEqualTo("Kitchen");
        assertThat(c.getCaseType()).isNotEqualTo("Water");
        assertThat(c.getTenant().getTenantId() == 1).isTrue();
        assertThat(c.getManager().getManagerId() == 1).isTrue();
    }

    @Test
    public void whenGettingCasesByFixDate_ThenGetCorrectOrder() {
        List<Case> cases = managerService.getCasesByFixDate();
        assertThat(cases.get(0).getFixDate()).isEqualTo("2018-07-01");
        assertThat(cases.get(0).getFixDate()).isNotEqualTo("2018-07-02");
        assertThat(cases.get(1).getFixDate()).isEqualTo("2018-07-02");
        assertThat(cases.get(1).getFixDate()).isNotEqualTo("2018-07-01");
    }

    @Test
    public void whenCreatingMessage_ThenNoException() {
        CaseMessage caseMessage = new CaseMessage();
        caseMessage.setMessageText("Test");
        caseMessage.setCase(new Case(2));

        managerService.createMessage(caseMessage);

        CaseMessage created = managerService.findMessage(3);
        assertThat(created.getMessageText()).isEqualTo("Test");
    }

    @Test
    public void whenGettingMessagesByCase_ThenReturnTwo() {
        List<CaseMessage> caseMessages = managerService.getMessagesByCase(1);
        assertThat(caseMessages).hasSize(2);
    }

}